package org.codetome.hexameter.api;

import java.util.Collection;
import java.util.Map;

import org.codetome.hexameter.api.exception.HexagonNotFoundException;

/**
 * <p>
 * Represents a hexagonal grid. Use {@link HexagonalGridBuilder} to generate a
 * ready-to-use grid. This interface contains all common functionality for
 * </p>
 * <ul>
 * <li>getting a {@link Hexagon} by its grid coordinates</li>
 * <li>getting the neighbors of a {@link Hexagon}</li>
 * <li>calculating the distance (in hexagons) between two {@link Hexagon}s</li>
 * <li>calculating the movement range starting from a {@link Hexagon} using a
 * range</li>
 * <li>getting a {@link Hexagon} by a pixel coordinate</li>
 * </ul>
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
	Collection<Hexagon> getHexagons();

	/**
	 * Returns all {@link Hexagon}s contained in the given axial coordinate range.
	 * If the range contains coordinates which are not part of the grid it will throw a {@link HexagonNotFoundException}.
	 *
	 * @param from
	 * @param to
	 * @return {@link Hexagon}s in the given range.
	 */
	Collection<Hexagon> getHexagonsByAxialRange(AxialCoordinate from, AxialCoordinate to);

	/**
	 * Returns all {@link Hexagon}s contained in the given offset coordinate range.
	 *
	 * @param gridXFrom from x inclusive
	 * @param gridXTo to x inclusive
	 * @param gridYfrom from z inclusive
	 * @param gridYTo to z inclusive
	 * @return {@link Hexagon}s in the given range.
	 */
	Collection<Hexagon> getHexagonsByOffsetRange(int gridXFrom, int gridXTo, int gridYfrom, int gridYTo);

	/**
	 * Adds a new {@link Hexagon} at the given coordinate.
	 *
	 * @param coordinate
	 * @return new {@link Hexagon} created.
	 */
	Hexagon addHexagon(AxialCoordinate coordinate);

	/**
	 * Removes the {@link Hexagon} at the given coordinate.
	 *
	 * @param coordinate
	 * @return {@link Map#remove(Object)}
	 */
	Hexagon removeHexagon(AxialCoordinate coordinate);

	/**
	 * Tells whether the given axial coordinate is on the grid or not.
	 * If you want to look up by offset coordinate use {@link CoordinateConverter}.
	 *
	 * @param coordinate
	 * @return is it on the grid?
	 */
	boolean containsAxialCoordinate(AxialCoordinate coordinate);

	/**
	 * Fetches a {@link Hexagon} by its axial coordinate. If no {@link Hexagon} found at the given location it throws a
	 * {@link HexagonNotFoundException}.
	 *
	 * @param coordinate
	 * @return {@link Hexagon}
	 */
	Hexagon getByAxialCoordinate(AxialCoordinate coordinate);

	/**
	 * Returns a {@link Hexagon} by a pixel coordinate. Throws {@link HexagonNotFoundException} if there were no
	 * {@link Hexagon} at the given coordinates.
	 * <em>Please note</em> that all pixel coordinates are relative to
	 * the containing {@link HexagonalGrid}.
	 *
	 * @param x pixel x coordinate
	 * @param y pixel y coordinate
	 * @return {@link Hexagon}
	 */
	Hexagon getByPixelCoordinate(double x, double y);

	/**
	 * Returns all neighbors of a {@link Hexagon}.
	 *
	 * @param hexagon {@link Hexagon}
	 * @return the {@link Hexagon}'s neighbors
	 */
	Collection<Hexagon> getNeighborsOf(Hexagon hexagon);

	/**
	 * Clears all satellite data attached to the {@link Hexagon}s in this grid.
	 */
	void clearSatelliteData();
}
