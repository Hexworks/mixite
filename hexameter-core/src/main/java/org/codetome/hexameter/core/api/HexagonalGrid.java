package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;
import rx.Observable;

import java.util.Collection;

/**
 * <p>
 * Represents a hexagonal grid. Use {@link HexagonalGridBuilder} to generate a
 * ready-to-use grid. This interface contains all common functionality for dealing with
 * Hexagons. See {@link HexagonalGridCalculator} for more advanced features.
 * </p>
 * <p>
 * This {@link HexagonalGrid} uses an cube (trapezoidal) coordinate system for easier
 * computation. This means that apart from the X axis a diagonal axis is used instead of
 * the vertical Y axis.
 * </p>
 */
public interface HexagonalGrid {

    /**
     * Returns this HexagonalGrid's GridData.
     *
     * @return grid data
     */
    GridData getGridData();

    /**
     * Returns all {@link Hexagon}s contained in this grid.
     *
     * @return hexagons
     */
    Observable<Hexagon> getHexagons();

    /**
     * Returns all {@link Hexagon}s contained in the given cube coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param from from
     * @param to to
     *
     * @return {@link Hexagon}s in the given range.
     */
    Observable<Hexagon> getHexagonsByCubeRange(CubeCoordinate from, CubeCoordinate to);

    /**
     * Returns all {@link Hexagon}s contained in the given offset coordinate range.
     * If the range contains coordinates which are not part of the grid they will be ignored.
     *
     * @param gridXFrom from x inclusive
     * @param gridXTo to x inclusive
     * @param gridYFrom from z inclusive
     * @param gridYTo to z inclusive
     *
     * @return {@link Hexagon}s in the given range.
     */
    Observable<Hexagon> getHexagonsByOffsetRange(int gridXFrom, int gridXTo, int gridYFrom, int gridYTo);

    /**
     * Tells whether the given cube coordinate is on the grid or not.
     * If you want to look up by offset coordinate use {@link CoordinateConverter}.
     *
     * @param coordinate coord
     *
     * @return is it on the grid?
     */
    boolean containsCubeCoordinate(CubeCoordinate coordinate);

    /**
     * Returns a {@link Hexagon} by its cube coordinate.
     *
     * @param coordinate coord
     *
     * @return Optional with a Hexagon if it is present
     */
    Optional<Hexagon> getByCubeCoordinate(CubeCoordinate coordinate);

    /**
     * Returns a {@link Hexagon} by a pixel coordinate.
     * <em>Please note</em> that all pixel coordinates are relative to
     * the containing {@link HexagonalGrid}.
     *
     * @param coordinateX pixel coordinateX coordinate
     * @param coordinateY pixel coordinateY coordinate
     *
     * @return Optional with a Hexagon if it is present
     */
    Optional<Hexagon> getByPixelCoordinate(double coordinateX, double coordinateY);

    /**
     * Returns a neighbor of a Hexagon by its neighbor index.
     *
     * @return neighbor or empty Optional if not applicable
     */
    Optional<Hexagon> getNeighborByIndex(Hexagon hexagon, int index);

    /**
     * Returns all neighbors of a {@link Hexagon}.
     *
     * @param hexagon {@link Hexagon}
     *
     * @return the {@link Hexagon}'s neighbors
     */
    Collection<Hexagon> getNeighborsOf(Hexagon hexagon);

    /**
     * Clears all satellite data attached to the {@link Hexagon}s in this grid.
     */
    void clearSatelliteData();
}
