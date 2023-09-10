package org.hexworks.mixite2.core.api

import kotlin.jvm.JvmStatic

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
        return gridX.toString() + org.hexworks.mixite2.core.api.CubeCoordinate.Companion.SEP + gridZ
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
        @JvmStatic
        fun fromAxialKey(axialKey: String): org.hexworks.mixite2.core.api.CubeCoordinate {
            val result: org.hexworks.mixite2.core.api.CubeCoordinate
            try {
                val coords = axialKey.split(org.hexworks.mixite2.core.api.CubeCoordinate.Companion.SEP.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                result = org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates(
                    coords[0].toInt(),
                    coords[1].toInt()
                )
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
        @JvmStatic
        fun fromCoordinates(gridX: Int, gridZ: Int): org.hexworks.mixite2.core.api.CubeCoordinate {
            return org.hexworks.mixite2.core.api.CubeCoordinate(gridX, gridZ)
        }
    }
}
