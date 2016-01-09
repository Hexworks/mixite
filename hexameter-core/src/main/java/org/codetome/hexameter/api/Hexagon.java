package org.codetome.hexameter.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Represents a Hexagon.
 * </p>
 * <em>Please note</em> that all coordinates are relative to the {@link HexagonalGrid} containing this {@link Hexagon}.
 */
public interface Hexagon extends Serializable {

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
    List<Point> getPoints();

    /**
     * Returns the {@link AxialCoordinate} of this {@link Hexagon}.
     *
     * @return
     */
    AxialCoordinate getAxialCoordinate();

    /**
     * Returns this {@link Hexagon}'s <b>x</b> (axial) coordinate on the {@link HexagonalGrid}.
     *
     * @return x coordinate on the grid
     */
    int getGridX();

    /**
     * Returns this {@link Hexagon}'s <b>y</b> coordinate on the {@link HexagonalGrid}.
     * The Y coordinate is not present in the axial model but it is in the cube model.
     * This method is just for convenience.
     *
     * @return y coordinate on the grid
     */
    int getGridY();

    /**
     * Returns this {@link Hexagon}'s <b>z</b> (axial) coordinate on the {@link HexagonalGrid}.
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
     * @return optional satellite data
     */
    <T> Optional<T> getSatelliteData();

    /**
     * Can be used to add arbitrary satellite data to a {@link Hexagon}.
     *
     * @param data
     */
    <T> void setSatelliteData(T data);

    /**
     * Clears the satellite data of this Hexagon.
     */
    void clearSatelliteData();

}
