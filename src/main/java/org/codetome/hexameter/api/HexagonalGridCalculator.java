package org.codetome.hexameter.api;

import java.util.Set;

/**
 * Supports common operations on a {@link HexagonalGrid}.
 * Operations supported:
 * <ul>
 * <li>Calculating distance between 2 {@link Hexagon}s.</li>
 * <li>Calsulating movement range from a {@link Hexagon} using an arbitrary distance.</li>
 * </ul>
 * <em>Not implemented yet, but are on the roadmap:</em>
 * <ul>
 * <li>Calculating movement range with obstacles</li>
 * <li>Calculating field of view</li>
 * <li>Pathfinding between two {@link Hexagon}s (using obstacles)</li>
 * </ul>
 */
public interface HexagonalGridCalculator {

	/**
	 * Calculates the distance (in hexagons) between two {@link Hexagon} objects on the grid.
	 *
	 * @param hex0
	 * @param hex1
	 * @return distance
	 */
	int calculateDistanceBetween(Hexagon hex0, Hexagon hex1);

	/**
	 * Returns all {@link Hexagon}s which are within <code>distance</code> (inclusive) from the {@link Hexagon}.
	 *
	 * @param hexagon {@link Hexagon}
	 * @param distance
	 * @return {@link Hexagon}s within distance (inclusive)
	 */
	Set<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance);
}
