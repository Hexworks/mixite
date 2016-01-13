package org.codetome.hexameter.core.api;

import java.util.Optional;

/**
 * <p>
 * Represents a hexagonal grid. Use {@link HexagonalGridBuilder} to generate a
 * ready-to-use grid. This interface contains all common functionality for dealing with
 * Hexagons. See {@link HexagonalGridCalculator} for more advanced features.
 * </p>
 *
 * This {@link HexagonalGrid} uses an axial (trapezoidal) coordinate system for easier
 * computation. This means that apart from the X axis a diagonal axis is used instead of
 * the vertical Y axis.
 */
public interface HexagonalGrid {

    /**
     * Returns all {@link Hexagon}s contained in this grid.
     *
     * @return hexagons
     */
    Iterable<Hexagon> getHexagons();

    /**
     * Returns all {@link Hexagon}s contained in the given axial coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param from
     * @param to
     *
     * @return {@link Hexagon}s in the given range.
     */
    Iterable<Hexagon> getHexagonsByAxialRange(AxialCoordinate from, AxialCoordinate to);

    /**
     * Returns all {@link Hexagon}s contained in the given offset coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param gridXFrom from x inclusive
     * @param gridXTo to x inclusive
     * @param gridYfrom from z inclusive
     * @param gridYTo to z inclusive
     *
     * @return {@link Hexagon}s in the given range.
     */
    Iterable<Hexagon> getHexagonsByOffsetRange(int gridXFrom, int gridXTo, int gridYfrom, int gridYTo);

    /**
     * Adds a new {@link Hexagon} at the given coordinate.
     * If there is already a Hexagon at the given position it is overwritten.
     *
     * @param coordinate
     *
     * @return new {@link Hexagon} created.
     */
    Hexagon addHexagon(AxialCoordinate coordinate);

    /**
     * Removes the {@link Hexagon} at the given coordinate.
     *
     * @param coordinate
     *
     * @return the removed {@link Hexagon}
     */
    Hexagon removeHexagon(AxialCoordinate coordinate);

    /**
     * Tells whether the given axial coordinate is on the grid or not.
     * If you want to look up by offset coordinate use {@link CoordinateConverter}.
     *
     * @param coordinate
     *
     * @return is it on the grid?
     */
    boolean containsAxialCoordinate(AxialCoordinate coordinate);

    /**
     * Returns a {@link Hexagon} by its axial coordinate.
     *
     * @param coordinate
     *
     * @return Optional with a Hexagon if it is present
     */
    Optional<Hexagon> getByAxialCoordinate(AxialCoordinate coordinate);

    /**
     * Returns a {@link Hexagon} by a pixel coordinate.
     * <em>Please note</em> that all pixel coordinates are relative to
     * the containing {@link HexagonalGrid}.
     *
     * @param x pixel x coordinate
     * @param y pixel y coordinate
     *
     * @return Optional with a Hexagon if it is present
     */
    Optional<Hexagon> getByPixelCoordinate(double x, double y);

    /**
     * Returns all neighbors of a {@link Hexagon}.
     *
     * @param hexagon {@link Hexagon}
     *
     * @return the {@link Hexagon}'s neighbors
     */
    Iterable<Hexagon> getNeighborsOf(Hexagon hexagon);

    /**
     * Clears all satellite data attached to the {@link Hexagon}s in this grid.
     */
    void clearSatelliteData();
}
