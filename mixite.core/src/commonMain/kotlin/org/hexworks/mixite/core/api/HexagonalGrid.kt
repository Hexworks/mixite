package org.hexworks.mixite.core.api

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.internal.GridData

/**
 *
 *
 * Represents a hexagonal grid. Use [HexagonalGridBuilder] to generate a
 * ready-to-use grid. This interface contains all common functionality for dealing with
 * Hexagons. See [HexagonalGridCalculator] for more advanced features.
 *
 *
 *
 * This [HexagonalGrid] uses an cube (trapezoidal) coordinate system for easier
 * computation. This means that apart from the X axis a diagonal axis is used instead of
 * the vertical Y axis.
 *
 */
interface HexagonalGrid<T : SatelliteData> {

    /**
     * Returns this HexagonalGrid's GridData.
     *
     * @return grid data
     */
    val gridData: GridData

    /**
     * Returns all [Hexagon]s contained in this grid.
     *
     * @return hexagons
     */
    val hexagons: Iterable<Hexagon<T>>

    /**
     * Returns all [Hexagon]s contained in the given cube coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param from from
     * @param to to
     *
     * @return [Hexagon]s in the given range.
     */
    fun getHexagonsByCubeRange(from: CubeCoordinate, to: CubeCoordinate): Iterable<Hexagon<T>>

    /**
     * Returns all [Hexagon]s contained in the given offset coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param gridXFrom from x inclusive
     * @param gridXTo to x inclusive
     * @param gridYFrom from z inclusive
     * @param gridYTo to z inclusive
     *
     * @return [Hexagon]s in the given range.
     */
    fun getHexagonsByOffsetRange(gridXFrom: Int, gridXTo: Int, gridYFrom: Int, gridYTo: Int): Iterable<Hexagon<T>>

    /**
     * Tells whether the given cube coordinate is on the grid or not.
     * If you want to look up by offset coordinate use [CoordinateConverter].
     *
     * @param coordinate coord
     *
     * @return is it on the grid?
     */
    fun containsCubeCoordinate(coordinate: CubeCoordinate): Boolean

    /**
     * Returns a [Hexagon] by its cube coordinate.
     *
     * @param coordinate coord
     *
     * @return Maybe with a Hexagon if it is present
     */
    fun getByCubeCoordinate(coordinate: CubeCoordinate): Maybe<Hexagon<T>>

    /**
     * Returns a [Hexagon] by a pixel coordinate.
     * *Please note* that all pixel coordinates are relative to
     * the containing [HexagonalGrid].
     *
     * @param coordinateX pixel coordinateX coordinate
     * @param coordinateY pixel coordinateY coordinate
     *
     * @return Maybe with a Hexagon if it is present
     */
    fun getByPixelCoordinate(coordinateX: Double, coordinateY: Double): Maybe<Hexagon<T>>

    /**
     * Returns the coordinate of a neighbor of a Hexagon by its neighbor index.
     *
     * @return CubeCoordinate
     */
    fun getNeighborCoordinateByIndex(coordinate: CubeCoordinate, index: Int): CubeCoordinate

    /**
     * Returns a neighbor of a Hexagon by its neighbor index.
     *
     * @return neighbor or empty Maybe if not applicable
     */
    fun getNeighborByIndex(hexagon: Hexagon<T>, index: Int): Maybe<Hexagon<T>>

    /**
     * Returns all neighbors of a [Hexagon].
     *
     * @param hexagon [Hexagon]
     *
     * @return the [Hexagon]'s neighbors
     */
    fun getNeighborsOf(hexagon: Hexagon<T>): Collection<Hexagon<T>>

}
