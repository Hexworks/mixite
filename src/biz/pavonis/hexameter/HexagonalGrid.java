package biz.pavonis.hexameter;

import java.util.List;

import biz.pavonis.hexameter.exception.HexagonNotFoundException;

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
	 * @return 2d {@link List} of {@link Hexagon}s.
	 */
	List<List<Hexagon>> getHexagons();

	/**
	 * Fetches a {@link Hexagon} by its grid coordinate. If no {@link Hexagon} found at the given location it throws a
	 * {@link HexagonNotFoundException}.
	 * 
	 * @param x grid x coordinate
	 * @param y grid y coordinate
	 * @return {@link Hexagon}
	 */
	Hexagon getByGridCoordinate(int x, int y);

	/**
	 * Returns all neighbors of a {@link Hexagon}.
	 * 
	 * @param hexagon {@link Hexagon}
	 * @return the {@link Hexagon}'s neighbors
	 */
	List<Hexagon> getNeighborsOf(Hexagon hexagon);

	/**
	 * Calculates the distance (in hexagons) between two {@link Hexagon} objects on the grid.
	 * 
	 * @param hex0
	 * @param hex1
	 * @return distance
	 */
	int calculateDistanceBetween(Hexagon hex0, Hexagon hex1);

	/**
	 * Returns all {@link Hexagon}s which are within distance (inclusive) from the {@link Hexagon}.
	 * 
	 * @param hexagon {@link Hexagon}
	 * @param distance
	 * @return {@link Hexagon}s within distance (inclusive)
	 */
	List<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance);

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
	Hexagon getByPixelCoordinate(int x, int y);
}
