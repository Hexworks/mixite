package org.hexworks.mixite.core.api

/**
 * Represents a right or left angle (60°) of a Hexagon rotation.
 * See: http://www.redblobgames.com/grids/hexagons/#rotation
 */
enum class RotationDirection constructor(private val rotationCalculator: RotationCalculator) {

    RIGHT(object : RotationCalculator {
        override fun calculate(coord: CubeCoordinate): CubeCoordinate {
            return CubeCoordinate.fromCoordinates(-coord.gridZ, -coord.gridY)
        }
    }),
    LEFT(object : RotationCalculator {
        override fun calculate(coord: CubeCoordinate): CubeCoordinate {
            return CubeCoordinate.fromCoordinates(-coord.gridY, -coord.gridX)
        }
    });

    /**
     * Calculates a rotation (right or left) of `coord`.
     *
     * @param coord coordinate to rotate
     *
     * @return rotated coordinate
     */
    fun calculateRotation(coord: CubeCoordinate): CubeCoordinate {
        return rotationCalculator.calculate(coord)
    }

    internal interface RotationCalculator {
        fun calculate(coord: CubeCoordinate): CubeCoordinate
    }
}
