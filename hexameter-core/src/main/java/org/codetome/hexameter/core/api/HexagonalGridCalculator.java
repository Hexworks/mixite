package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.backport.Optional;

import java.util.Set;

/**
 * Supports advanced operations on a {@link HexagonalGrid}.
 * Operations supported:
 * <ul>
 * <li>Calculating distance between 2 {@link Hexagon}s.</li>
 * <li>Calculating movement range from a {@link Hexagon} using an arbitrary distance.</li>
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
     * @param hex0 hex 0
     * @param hex1 hex 1
     * @return distance
     */
    int calculateDistanceBetween(Hexagon hex0, Hexagon hex1);

    /**
     * Returns all {@link Hexagon}s which are within <code>distance</code> (inclusive) from the {@link Hexagon}.
     *
     * @param hexagon {@link Hexagon}
     * @param distance distance
     * @return {@link Hexagon}s within distance (inclusive)
     */
    Set<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance);

    /**
     * Returns the Hexagon on the grid which is at the point resulted by rotating the <code>targetHex</code>'s
     * coordinates around the <code>originalHex</code> by <code>rotationDirection</code> degrees.
     * @param originalHex center hex
     * @param targetHex hex to rotate
     * @param rotationDirection direction of the rotation
     * @return result
     */
    Optional<Hexagon> rotateHexagon(Hexagon originalHex, Hexagon targetHex, RotationDirection rotationDirection);


    /**
     * Returns the {@link Set} of {@link Hexagon}s which are <code>radius</code> distance
     * from <code>centerHexagon</code>.
     * @param centerHexagon center
     * @param radius radius
     * @return Set of hexagons or empty set if not applicable
     */
    Set<Hexagon> calculateRingFrom(Hexagon centerHexagon, int radius);
}
