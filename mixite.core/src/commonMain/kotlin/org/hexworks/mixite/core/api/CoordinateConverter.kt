package org.hexworks.mixite.core.api

import kotlin.jvm.JvmStatic

/**
 * Utility class for converting coordinated from the offset system to
 * the cube one (the library uses the latter).
 */
class CoordinateConverter {
    init {
        throw UnsupportedOperationException("This utility class is not meant to be instantiated.")
    }

    companion object {

        /**
         * Calculates the cube X coordinate based on an offset coordinate pair.
         *
         * @param offsetX offset x
         * @param offsetY offset y
         * @param orientation orientation
         *
         * @return cube x
         */
        @JvmStatic
        fun convertOffsetCoordinatesToCubeX(offsetX: Int, offsetY: Int, orientation: HexagonOrientation): Int {
            return if (HexagonOrientation.FLAT_TOP.equals(orientation)) offsetX else offsetX - offsetY / 2
        }

        /**
         * Calculates the cube Z coordinate based on an offset coordinate pair.
         *
         * @param offsetX offset x
         * @param offsetY offset y
         * @param orientation orientation
         *
         * @return cube z
         */
        @JvmStatic
        fun convertOffsetCoordinatesToCubeZ(offsetX: Int, offsetY: Int, orientation: HexagonOrientation): Int {
            return if (HexagonOrientation.FLAT_TOP.equals(orientation)) offsetY - offsetX / 2 else offsetY
        }

        /**
         * Calculates the offset row based on a CubeCoordinate.
         *
         * @Param cubeCoordinate a cube coordinate
         * @param orientation orientation
         *
         * @return offset row or y-value
         */
        @JvmStatic
        fun convertCubeCoordinateToOffsetRow(coordinate: CubeCoordinate, orientation: HexagonOrientation): Int {
            return if(HexagonOrientation.FLAT_TOP.equals(orientation)) {
                coordinate.gridZ + (coordinate.gridX - (coordinate.gridX and 1)) / 2
            } else {
                coordinate.gridZ
            }
        }

        /**
         * Calculates the offset column based on a CubeCoordinate.
         *
         * @Param cubeCoordinate a cube coordinate
         * @param orientation orientation
         *
         * @return offset column or x-value
         */
        @JvmStatic
        fun convertCubeCoordinateToOffsetColumn(coordinate: CubeCoordinate, orientation: HexagonOrientation): Int {
            return if(HexagonOrientation.FLAT_TOP.equals(orientation)) {
                coordinate.gridX
            } else {
                coordinate.gridX + (coordinate.gridZ - (coordinate.gridZ and 1)) / 2
            }
        }
    }

}
