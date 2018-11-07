package org.hexworks.mixite.core.internal.impl

import org.hexworks.mixite.core.api.*
import org.hexworks.mixite.core.api.contract.HexagonDataStorage
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.internal.GridData
import org.hexworks.mixite.core.vendor.Maybe
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Default implementation of the [Hexagon] interface.
 */
class HexagonImpl<T : SatelliteData> internal constructor(
        private val sharedData: GridData,
        override val cubeCoordinate: CubeCoordinate,
        private val hexagonDataStorage: HexagonDataStorage<T>) : Hexagon<T> {

    override val vertices: MutableList<Double>
    override val points: List<Point>
    override val externalBoundingBox: Rectangle
    override val internalBoundingBox: Rectangle

    override val id: String
        get() = cubeCoordinate.toAxialKey()

    override val gridX: Int
        get() = cubeCoordinate.gridX

    override val gridY: Int
        get() = cubeCoordinate.gridY

    override val gridZ: Int
        get() = cubeCoordinate.gridZ

    override val centerX: Double
        get() = if (HexagonOrientation.FLAT_TOP.equals(sharedData.orientation)) {
            cubeCoordinate.gridX * sharedData.hexagonWidth + sharedData.radius
        } else {
            cubeCoordinate.gridX * sharedData.hexagonWidth + cubeCoordinate.gridZ * sharedData.hexagonWidth / 2 + sharedData.hexagonWidth / 2
        }

    override val centerY: Double
        get() = if (HexagonOrientation.FLAT_TOP.equals(sharedData.orientation)) {
            cubeCoordinate.gridZ * sharedData.hexagonHeight + cubeCoordinate.gridX * sharedData.hexagonHeight / 2 + sharedData.hexagonHeight / 2
        } else {
            cubeCoordinate.gridZ * sharedData.hexagonHeight + sharedData.radius
        }

    override val satelliteData: Maybe<T>
        get() = hexagonDataStorage.getSatelliteDataBy(cubeCoordinate)

    init {

        this.points = calculatePoints()
        val x1 = points[3].coordinateX
        val y1 = points[2].coordinateY
        val x2 = points[0].coordinateX
        val y2 = points[5].coordinateY

        externalBoundingBox = Rectangle(x1, y1, x2 - x1, y2 - y1)
        internalBoundingBox = Rectangle((centerX - 1.25 * sharedData.radius / 2),
                (centerY - 1.25 * sharedData.radius / 2),
                (1.25f * sharedData.radius),
                (1.25f * sharedData.radius))

        this.vertices = ArrayList()
        for (point in points) {
            vertices.add(point.coordinateX)
            vertices.add(point.coordinateY)
        }
    }

    private fun calculatePoints(): List<Point> {
        val points = ArrayList<Point>()
        for (i in 0..5) {
            val angle = 2 * PI / 6 * (i + sharedData.orientation.coordinateOffset)
            val x = centerX + sharedData.radius * cos(angle)
            val y = centerY + sharedData.radius * sin(angle)
            points.add(Point.fromPosition(x, y))
        }
        return points
    }

    override fun setSatelliteData(data: T) {
        this.hexagonDataStorage.addCoordinate(cubeCoordinate, data)
    }

    override fun clearSatelliteData() {
        this.hexagonDataStorage.clearDataFor(cubeCoordinate)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as HexagonImpl<*>

        if (cubeCoordinate != other.cubeCoordinate) return false

        return true
    }

    override fun hashCode(): Int {
        return cubeCoordinate.hashCode()
    }


}
