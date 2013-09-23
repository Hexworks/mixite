package biz.pavonis.hexameter.api;

import java.util.Map;
import java.util.Set;

import biz.pavonis.hexameter.api.exception.HexagonNotFoundException;

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
	Map<String, Hexagon> getHexagons();

	/**
	 * Returns all {@link Hexagon}s contained in the given axial coordinate range.
	 * 
	 * @param gridXFrom from x inclusive
	 * @param gridXTo to x inclusive
	 * @param gridZfrom from z inclusive
	 * @param gridZTo to z inclusive
	 * @return {@link Hexagon}s in the given range.
	 */
	Map<String, Hexagon> getHexagonGridByAxialRange(int gridXFrom, int gridXTo, int gridZfrom, int gridZTo);

	/**
	 * Returns all {@link Hexagon}s contained in the given offset coordinate range.
	 * 
	 * @param gridXFrom from x inclusive
	 * @param gridXTo to x inclusive
	 * @param gridYfrom from z inclusive
	 * @param gridYTo to z inclusive
	 * @return {@link Hexagon}s in the given range.
	 */
	Map<String, Hexagon> getHexagonGridByOffsetRange(int gridXFrom, int gridXTo, int gridYfrom, int gridYTo);

	/**
	 * Adds a new {@link Hexagon} at the given coordinates.
	 * 
	 * @param gridX
	 * @param gridZ
	 * @return new {@link Hexagon} created.
	 */
	Hexagon addHexagon(int gridX, int gridZ);

	/**
	 * Removes the {@link Hexagon} at the given coordinates.
	 * 
	 * @param gridX
	 * @param gridZ
	 * @return {@link Map#remove(Object)}
	 */
	Hexagon removeHexagon(int gridX, int gridZ);

	/**
	 * Tells whether the given axial coordinate is on the grid or not.
	 * If you want to look up by offset coordinate use {@link CoordinateConverter}.
	 * 
	 * @param gridX
	 * @param gridZ
	 * @return is it on the grid?
	 */
	boolean containsCoordinate(int gridX, int gridZ);

	/**
	 * Fetches a {@link Hexagon} by its grid coordinate. If no {@link Hexagon} found at the given location it throws a
	 * {@link HexagonNotFoundException}.
	 * 
	 * @param gridX grid x coordinate
	 * @param gridZ grid z coordinate
	 * @return {@link Hexagon}
	 */
	Hexagon getByGridCoordinate(int gridX, int gridZ);

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
	Set<Hexagon> getNeighborsOf(Hexagon hexagon);

	/**
	 * Clears all satellite data attached to the {@link Hexagon}s in this grid.
	 */
	void clearSatelliteData();
}
