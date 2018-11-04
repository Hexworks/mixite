package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.vendor.Maybe

/**
 * Supports advanced operations on a [HexagonalGrid].
 * Operations supported:
 *
 *  * Calculating distance between 2 [Hexagon]s.
 *  * Calculating movement range from a [Hexagon] using an arbitrary distance.
 *
 * *Not implemented yet, but are on the roadmap:*
 *
 *  * Calculating movement range with obstacles
 *  * Calculating field of view
 *  * Path finding between two [Hexagon]s (using obstacles)
 *
 */
interface HexagonalGridCalculator<T : SatelliteData> {

    /**
     * Calculates the distance (in hexagons) between two [Hexagon] objects on the grid.
     *
     * @param hex0 hex 0
     * @param hex1 hex 1
     *
     * @return distance
     */
    fun calculateDistanceBetween(hex0: Hexagon<T>, hex1: Hexagon<T>): Int

    /**
     * Returns all [Hexagon]s which are within `distance` (inclusive) from the [Hexagon].
     *
     * @param hexagon [Hexagon]
     * @param distance distance
     *
     * @return [Hexagon]s within distance (inclusive)
     */
    fun calculateMovementRangeFrom(hexagon: Hexagon<T>, distance: Int): Set<Hexagon<T>>

    /**
     * Returns the Hexagon on the grid which is at the point resulted by rotating the `targetHex`'s
     * coordinates around the `originalHex` by `rotationDirection` degrees.
     *
     * @param originalHex center hex
     * @param targetHex hex to rotate
     * @param rotationDirection direction of the rotation
     *
     * @return result
     */
    fun rotateHexagon(originalHex: Hexagon<T>, targetHex: Hexagon<T>, rotationDirection: RotationDirection): Maybe<Hexagon<T>>


    /**
     * Returns the [Set] of [Hexagon]s which are `radius` distance
     * from `centerHexagon`.
     *
     * @param centerHexagon center
     * @param radius radius
     *
     * @return Set of hexagons or empty set if not applicable
     */
    fun calculateRingFrom(centerHexagon: Hexagon<T>, radius: Int): Set<Hexagon<T>>

    /**
     * Returns a [List] of [Hexagon]s which must be traversed in the
     * given order to go from the `from` Hexagon to the `to` Hexagon.
     *
     * @param from starting hexagon
     * @param to target hexagon
     *
     * @return List of hexagons containing the line
     */
    fun drawLine(from: Hexagon<T>, to: Hexagon<T>): List<Hexagon<T>>

    /**
     * Returns true if the `from` [Hexagon] is visible from the `to` Hexagon.
     *
     * @param from the Hexagon that we are testing the visibility from
     * @param to the Hexagon from which we are testing the visibility to
     *
     * @return true if hexagon is visible, false otherwise
     */
    fun isVisible(from: Hexagon<T>, to: Hexagon<T>): Boolean
}
