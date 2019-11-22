package org.hexworks.mixite.core.internal.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite.core.api.*
import org.hexworks.mixite.core.api.contract.HexagonDataStorage
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.internal.GridData
import kotlin.math.abs

class HexagonalGridImpl<T : SatelliteData>(builder: HexagonalGridBuilder<T>) : HexagonalGrid<T> {

    override val gridData: GridData = builder.gridData
    private val hexagonDataStorage: HexagonDataStorage<T> = builder.getHexagonDataStorage()

    override val hexagons: Iterable<Hexagon<T>>
        get() {
            val coordIter = hexagonDataStorage.coordinates.iterator()

            return object : Iterable<Hexagon<T>> {
                override fun iterator(): Iterator<Hexagon<T>> {
                    return object : Iterator<Hexagon<T>> {
                        override fun hasNext(): Boolean {
                            return coordIter.hasNext()
                        }

                        override fun next(): Hexagon<T> {
                            return hexagon(coordIter.next())
                        }
                    }
                }
            }
        }

    init {
        for (cubeCoordinate in builder.gridLayoutStrategy.fetchGridCoordinates(builder)) {
            this@HexagonalGridImpl.hexagonDataStorage.addCoordinate(cubeCoordinate)
        }
    }

    private fun hexagon(coordinate: CubeCoordinate): HexagonImpl<T> {
        return HexagonImpl(gridData, coordinate, hexagonDataStorage)
    }

    override fun getHexagonsByCubeRange(from: CubeCoordinate, to: CubeCoordinate): Iterable<Hexagon<T>> {
        val coordinates = ArrayList<CubeCoordinate>(abs(from.gridZ-to.gridZ) + abs(from.gridX-to.gridX))

        for (gridZ in from.gridZ..to.gridZ) {
            for (gridX in from.gridX..to.gridX) {
                val coord = CubeCoordinate.fromCoordinates(gridX, gridZ)
                if (containsCubeCoordinate(coord)) {
                    coordinates.add(coord)
                }
            }
        }

        val coordIter = coordinates.iterator()

        return object : Iterable<Hexagon<T>> {
            override fun iterator(): Iterator<Hexagon<T>> {
                return object : Iterator<Hexagon<T>> {
                    override fun hasNext(): Boolean {
                        return coordIter.hasNext()
                    }

                    override fun next(): Hexagon<T> {
                        return hexagon(coordIter.next())
                    }
                }
            }
        }
    }

    override fun getHexagonsByOffsetRange(gridXFrom: Int, gridXTo: Int, gridYFrom: Int, gridYTo: Int): Iterable<Hexagon<T>> {
        val coords = ArrayList<CubeCoordinate>()

        for (gridX in gridXFrom..gridXTo) {
            for (gridY in gridYFrom..gridYTo) {
                val cubeX = CoordinateConverter.convertOffsetCoordinatesToCubeX(gridX, gridY, gridData.orientation)
                val cubeZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(gridX, gridY, gridData.orientation)
                val coord = CubeCoordinate.fromCoordinates(cubeX, cubeZ)
                if (containsCubeCoordinate(coord)) {
                    coords.add(coord)
                }
            }
        }

        val coordIter = coords.iterator()

        return object : Iterable<Hexagon<T>> {
            override fun iterator(): Iterator<Hexagon<T>> {
                return object : Iterator<Hexagon<T>> {
                    override fun hasNext(): Boolean {
                        return coordIter.hasNext()
                    }

                    override fun next(): Hexagon<T> {
                        return hexagon(coordIter.next())
                    }
                }
            }
        }
    }

    override fun containsCubeCoordinate(coordinate: CubeCoordinate): Boolean {
        return this.hexagonDataStorage.containsCoordinate(coordinate)
    }

    override fun getByCubeCoordinate(coordinate: CubeCoordinate): Maybe<Hexagon<T>> {
        return if (containsCubeCoordinate(coordinate))
            Maybe.of(hexagon(coordinate))
        else
            Maybe.empty()
    }

    override fun getByPixelCoordinate(coordinateX: Double, coordinateY: Double): Maybe<Hexagon<T>> {
        var estimatedGridX = (coordinateX / gridData.hexagonWidth).toInt()
        var estimatedGridZ = (coordinateY / gridData.hexagonHeight).toInt()
        estimatedGridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(estimatedGridX, estimatedGridZ, gridData.orientation)
        estimatedGridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(estimatedGridX, estimatedGridZ, gridData.orientation)
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        val estimatedCoordinate = CubeCoordinate.fromCoordinates(estimatedGridX, estimatedGridZ)
        val tempHex = HexagonImpl(gridData, estimatedCoordinate, hexagonDataStorage)
        val trueHex = refineHexagonByPixel(tempHex, Point.fromPosition(coordinateX, coordinateY))
        return if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            getByCubeCoordinate(estimatedCoordinate)
        } else {
            if (containsCubeCoordinate(trueHex.cubeCoordinate)) Maybe.of(trueHex) else Maybe.empty()
        }
    }

    override fun getNeighborByIndex(hexagon: Hexagon<T>, index: Int): Maybe<Hexagon<T>> {
        val neighborGridX = hexagon.gridX + NEIGHBORS[index][NEIGHBOR_X_INDEX]
        val neighborGridZ = hexagon.gridZ + NEIGHBORS[index][NEIGHBOR_Z_INDEX]
        val neighborCoordinate = CubeCoordinate.fromCoordinates(neighborGridX, neighborGridZ)
        return getByCubeCoordinate(neighborCoordinate)
    }

    override fun getNeighborsOf(hexagon: Hexagon<T>): Collection<Hexagon<T>> {
        val neighbors = HashSet<Hexagon<T>>()
        for (i in NEIGHBORS.indices) {
            val retHex = getNeighborByIndex(hexagon, i)
            if (retHex.isPresent) {
                neighbors.add(retHex.get())
            }
        }
        return neighbors
    }

    private fun hexagonsAreAtTheSamePosition(hex0: Hexagon<T>, hex1: Hexagon<T>): Boolean {
        return hex0.gridX == hex1.gridX && hex0.gridZ == hex1.gridZ
    }

    private fun refineHexagonByPixel(hexagon: Hexagon<T>, clickedPoint: Point): Hexagon<T> {
        var refined: Hexagon<T> = hexagon
        var smallestDistance = clickedPoint.distanceFrom(Point.fromPosition(refined.center.coordinateX, refined.center.coordinateY))
        for (neighbor in getNeighborsOf(hexagon)) {
            val currentDistance = clickedPoint.distanceFrom(Point.fromPosition(neighbor.center.coordinateX, neighbor.center.coordinateY))
            if (currentDistance < smallestDistance) {
                refined = neighbor
                smallestDistance = currentDistance
            }
        }
        return refined
    }

    companion object {

        private val NEIGHBORS = arrayOf(intArrayOf(+1, 0), intArrayOf(+1, -1), intArrayOf(0, -1), intArrayOf(-1, 0), intArrayOf(-1, +1), intArrayOf(0, +1))
        private const val NEIGHBOR_X_INDEX = 0
        private const val NEIGHBOR_Z_INDEX = 1
    }
}
