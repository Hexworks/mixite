package org.hexworks.mixite.core.api

import kotlin.math.sqrt

/**
 * Represents a point. Please note that this represents a point in
 * 2d space not an abstract concept of a coordinate.
 */
@Suppress("DataClassPrivateConstructor")
data class Point private constructor(val coordinateX: Double,
                                     val coordinateY: Double) {

    /**
     * Calculates a distance between two points.
     *
     * @param point point
     *
     * @return distance
     */
    fun distanceFrom(point: Point): Double {
        return sqrt((this.coordinateX - point.coordinateX) * (this.coordinateX - point.coordinateX) + (this.coordinateY - point.coordinateY) * (this.coordinateY - point.coordinateY))
    }

    companion object {

        /**
         * Creates a point from coordinateX and coordinateY positions.
         *
         * @param coordinateX x
         * @param coordinateY y
         *
         * @return point
         */
        fun fromPosition(coordinateX: Double, coordinateY: Double): Point {
            return Point(coordinateX, coordinateY)
        }
    }
}
