package org.hexworks.mixite2.core.api

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite2.core.api.contract.SatelliteData

/**
 * Represents a Hexagon.
 * *Please note* that all coordinates are relative to the [HexagonalGrid] containing this [Hexagon].
 */
interface Hexagon<T : SatelliteData> {

    /**
     * Returns an unique [String] representing this [Hexagon].
     */
    val id: String

    /**
     * Returns a list containing the [Point]s of this [Hexagon].
     */
    val points: List<Point>

    /**
     * Returns an array of the vertices of this [Hexagon].
     */
    val vertices: List<Double>

    /**
     * Returns a rectangle defining the **external** boundary box of this [Hexagon]
     * (a rectangle that hits the 2 pointy corners and the 2 flat sides).
     */
    val externalBoundingBox: Rectangle

    /**
     * Returns a rectangle defining the **internal** boundary box of this [Hexagon]
     * (the biggest rectangle that hits the outline of the [Hexagon] exactly 4 times).
     */
    val internalBoundingBox: Rectangle

    /**
     * Returns the [CubeCoordinate] of this [Hexagon].
     */
    val cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate

    /**
     * Returns this [Hexagon]'s **x** (cube) coordinate on the [HexagonalGrid].
     */
    val gridX: Int

    /**
     * Returns this [Hexagon]'s **y** coordinate on the [HexagonalGrid].
     * The Y coordinate is not present in the cube model but it is in the cube model.
     * This method is just for convenience.
     */
    val gridY: Int

    /**
     * Returns this [Hexagon]'s **z** (cube) coordinate on the [HexagonalGrid].
     */
    val gridZ: Int

    /**
     * Returns the center (pixel) [Point] of this [Hexagon].
     */
    val center: Point

    /**
     * Returns this [Hexagon]'s satellite data.
     */
    val satelliteData: Maybe<T>

    /**
     * Can be used to add arbitrary satellite data to a [Hexagon].
     */
    fun setSatelliteData(data: T)

    /**
     * Clears the satellite data of this Hexagon.
     */
    fun clearSatelliteData()

}
