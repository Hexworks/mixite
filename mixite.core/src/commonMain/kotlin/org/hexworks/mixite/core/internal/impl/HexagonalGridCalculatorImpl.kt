package org.hexworks.mixite.core.internal.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite.core.api.*
import org.hexworks.mixite.core.api.contract.SatelliteData
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class HexagonalGridCalculatorImpl<T : SatelliteData>(private val hexagonalGrid: HexagonalGrid<T>) : HexagonalGridCalculator<T> {

    override fun calculateDistanceBetween(hex0: Hexagon<T>, hex1: Hexagon<T>): Int {
        val absX = abs(hex0.gridX - hex1.gridX)
        val absY = abs(hex0.gridY - hex1.gridY)
        val absZ = abs(hex0.gridZ - hex1.gridZ)
        return max(max(absX, absY), absZ)
    }

    override fun calculateMovementRangeFrom(hexagon: Hexagon<T>, distance: Int): Set<Hexagon<T>> {
        val ret = HashSet<Hexagon<T>>()
        for (x in -distance..distance) {
            for (y in max(-distance, -x - distance)..min(distance, -x + distance)) {
                val z = -x - y
                val tempCoordinate = CubeCoordinate.fromCoordinates(hexagon.gridX + x, hexagon.gridZ + z)
                hexagonalGrid.getByCubeCoordinate(tempCoordinate).ifPresent { ret += it }
            }
        }
        return ret
    }

    override fun rotateHexagon(originalHex: Hexagon<T>, targetHex: Hexagon<T>, rotationDirection: RotationDirection): Maybe<Hexagon<T>> {
        val diffX = targetHex.gridX - originalHex.gridX
        val diffZ = targetHex.gridZ - originalHex.gridZ
        val diffCoord = CubeCoordinate.fromCoordinates(diffX, diffZ)
        val rotatedCoord = rotationDirection.calculateRotation(diffCoord)
        val resultCoord = CubeCoordinate.fromCoordinates(
                originalHex.gridX + rotatedCoord.gridX,
                originalHex.gridZ + rotatedCoord.gridZ) // 0, x,
        return hexagonalGrid.getByCubeCoordinate(resultCoord)
    }

    override fun calculateRingFrom(centerHexagon: Hexagon<T>, radius: Int): Set<Hexagon<T>> {
        val result = HashSet<Hexagon<T>>()
        val startingCoordinate = CubeCoordinate.fromCoordinates(
                centerHexagon.gridX - radius,
                centerHexagon.gridZ + radius
        )
        val startingHexagon = hexagonalGrid.getByCubeCoordinate(startingCoordinate)

        if (startingHexagon.isPresent) {
            var currentHexagon = startingHexagon.get()
            for (i in 0 until 6) {
                for (j in 0 until radius) {
                    val neighbor = hexagonalGrid.getNeighborByIndex(currentHexagon, i)
                    if (neighbor.isPresent) {
                        currentHexagon = neighbor.get()
                        result.add(currentHexagon)
                    } else {
                        return result
                    }
                }
            }
        }

        return result
    }

    override fun drawLine(from: Hexagon<T>, to: Hexagon<T>): List<Hexagon<T>> {
        val distance = calculateDistanceBetween(from, to)
        if (distance == 0) {
            return emptyList()
        }
        val results = ArrayList<Hexagon<T>>(distance+1)
        for (i in 0..distance) {
            val interpolatedCoordinate = cubeLinearInterpolate(from.cubeCoordinate,
                    to.cubeCoordinate, 1.0 / distance * i)
            results.add(hexagonalGrid.getByCubeCoordinate(interpolatedCoordinate).get())
        }
        return results
    }

    override fun isVisible(from: Hexagon<T>, to: Hexagon<T>): Boolean {
        val traversePath = drawLine(from, to)
        for (pathHexagon in traversePath) {
            if (pathHexagon.equals(from) || pathHexagon.equals(to)) {
                continue
            }
            if (pathHexagon.satelliteData.isPresent && pathHexagon.satelliteData.get().isOpaque) {
                return false
            }
        }
        return true
    }

    private fun cubeLinearInterpolate(from: CubeCoordinate, to: CubeCoordinate, sample: Double): CubeCoordinate {
        return roundToCubeCoordinate(linearInterpolate(from.gridX, to.gridX, sample),
                linearInterpolate(from.gridY, to.gridY, sample),
                linearInterpolate(from.gridZ, to.gridZ, sample))
    }

    private fun linearInterpolate(from: Int, to: Int, sample: Double): Double {
        return from + (to - from) * sample
    }

    private fun roundToCubeCoordinate(gridX: Double, gridY: Double, gridZ: Double): CubeCoordinate {
        var rx = round(gridX).toInt()
        val ry = round(gridY).toInt()
        var rz = round(gridZ).toInt()

        val differenceX = abs(rx - gridX)
        val differenceY = abs(ry - gridY)
        val differenceZ = abs(rz - gridZ)

        if (differenceX > differenceY && differenceX > differenceZ) {
            rx = -ry - rz
        } else if (differenceY <= differenceZ) {
            rz = -rx - ry
        }
        return CubeCoordinate.fromCoordinates(rx, rz)
    }
}
