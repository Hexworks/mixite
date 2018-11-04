package org.hexworks.mixite.core.api

/**
 * Represents a cube coordinate pair.
 * See http://www.redblobgames.com/grids/hexagons/#coordinates to learn more.
 * Note that the y coordinate is not stored in this object since it can be
 * calculated.
 */
@Suppress("DataClassPrivateConstructor")
data class CubeCoordinate private constructor(val gridX: Int, val gridZ: Int) {

    val gridY: Int
        get() = -(gridX + gridZ)

    /**
     * Creates an axial (x, z) key which can be used in key-value storage objects based on this
     * [CubeCoordinate].
     *
     * @return key
     */
    fun toAxialKey(): String {
        return gridX.toString() + SEP + gridZ
    }

    companion object {

        const val SEP = ","

        /**
         * Tries to create an [CubeCoordinate] from a key which has the format:
         * `%gridX%,%gridZ%`.
         *
         * @param axialKey key
         *
         * @return coord
         */
        fun fromAxialKey(axialKey: String): CubeCoordinate {
            val result: CubeCoordinate
            try {
                val coords = axialKey.split(SEP.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                result = fromCoordinates(coords[0].toInt(), coords[1].toInt())
            } catch (e: Exception) {
                throw IllegalArgumentException("Failed to create CubeCoordinate from key: $axialKey", e)
            }

            return result
        }

        /**
         * Creates an instance of [CubeCoordinate] from an x and a z coordinate.
         *
         * @param gridX grid x
         * @param gridZ grid z
         *
         * @return coord
         */
        fun fromCoordinates(gridX: Int, gridZ: Int): CubeCoordinate {
            return CubeCoordinate(gridX, gridZ)
        }
    }
}
