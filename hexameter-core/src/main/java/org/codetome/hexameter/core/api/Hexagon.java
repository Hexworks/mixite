package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.backport.Optional;

import java.util.Collection;

/**
 * Represents a Hexagon.
 * <em>Please note</em> that all coordinates are relative to the {@link HexagonalGrid} containing this {@link Hexagon}.
 */
public interface Hexagon {

    /**
     * Returns an unique {@link String} representing this {@link Hexagon}.
     *
     * @return id
     */
    String getId();

    /**
     * Returns a list containing the {@link Point}s of this {@link Hexagon}.
     *
     * @return points array
     */
    Collection<Point> getPoints();

    /**
     * Returns the {@link CubeCoordinate} of this {@link Hexagon}.
     *
     * @return cube coord
     */
    CubeCoordinate getCubeCoordinate();

    /**
     * Returns this {@link Hexagon}'s <b>x</b> (cube) coordinate on the {@link HexagonalGrid}.
     *
     * @return x coordinate on the grid
     */
    int getGridX();

    /**
     * Returns this {@link Hexagon}'s <b>y</b> coordinate on the {@link HexagonalGrid}.
     * The Y coordinate is not present in the cube model but it is in the cube model.
     * This method is just for convenience.
     *
     * @return y coordinate on the grid
     */
    int getGridY();

    /**
     * Returns this {@link Hexagon}'s <b>z</b> (cube) coordinate on the {@link HexagonalGrid}.
     *
     * @return z coordinate on the grid
     */
    int getGridZ();

    /**
     * Returns the center <b>x</b> (pixel) coordinate of this {@link Hexagon}.
     *
     * @return center x
     */
    double getCenterX();

    /**
     * Returns the center <b>y</b> (pixel) coordinate of this {@link Hexagon}.
     *
     * @return center y
     */
    double getCenterY();

    /**
     * Returns this {@link Hexagon}'s satellite data.
     *
     * @param <T> type of data
     * @return optional satellite data
     */
    <T extends SatelliteData> Optional<T> getSatelliteData();

    /**
     * Can be used to add arbitrary satellite data to a {@link Hexagon}.
     *
     * @param data data
     * @param <T>  type of data
     */
    <T extends SatelliteData> void setSatelliteData(T data);

    /**
     * Clears the satellite data of this Hexagon.
     */
    void clearSatelliteData();

}
