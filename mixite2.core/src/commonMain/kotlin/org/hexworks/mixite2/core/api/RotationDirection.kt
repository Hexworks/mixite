package org.hexworks.mixite2.core.api

/**
 * Represents a right or left angle (60°) of a Hexagon rotation.
 * See: http://www.redblobgames.com/grids/hexagons/#rotation
 */
enum class RotationDirection constructor(private val rotationCalculator: RotationCalculator) {

    RIGHT(object : RotationCalculator {
        override fun calculate(coord: org.hexworks.mixite2.core.api.CubeCoordinate): org.hexworks.mixite2.core.api.CubeCoordinate {
            return org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(-coord.gridZ, -coord.gridY)
        }
    }),
    LEFT(object : RotationCalculator {
        override fun calculate(coord: org.hexworks.mixite2.core.api.CubeCoordinate): org.hexworks.mixite2.core.api.CubeCoordinate {
            return org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(-coord.gridY, -coord.gridX)
        }
    });

    /**
     * Calculates a rotation (right or left) of `coord`.
     *
     * @param coord coordinate to rotate
     *
     * @return rotated coordinate
     */
    fun calculateRotation(coord: org.hexworks.mixite2.core.api.CubeCoordinate): org.hexworks.mixite2.core.api.CubeCoordinate {
        return rotationCalculator.calculate(coord)
    }

    internal interface RotationCalculator {
        fun calculate(coord: org.hexworks.mixite2.core.api.CubeCoordinate): org.hexworks.mixite2.core.api.CubeCoordinate
    }
}
