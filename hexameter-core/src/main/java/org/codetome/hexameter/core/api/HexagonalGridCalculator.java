package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;

import java.util.List;
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
 * <li>Path finding between two {@link Hexagon}s (using obstacles)</li>
 * </ul>
 */
public interface HexagonalGridCalculator<T extends SatelliteData> {

    /**
     * Calculates the distance (in hexagons) between two {@link Hexagon} objects on the grid.
     *
     * @param hex0 hex 0
     * @param hex1 hex 1
     *
     * @return distance
     */
    int calculateDistanceBetween(Hexagon<T> hex0, Hexagon<T> hex1);

    /**
     * Returns all {@link Hexagon}s which are within <code>distance</code> (inclusive) from the {@link Hexagon}.
     *
     * @param hexagon {@link Hexagon}
     * @param distance distance
     *
     * @return {@link Hexagon}s within distance (inclusive)
     */
    Set<Hexagon<T>> calculateMovementRangeFrom(Hexagon<T> hexagon, int distance);

    /**
     * Returns the Hexagon on the grid which is at the point resulted by rotating the <code>targetHex</code>'s
     * coordinates around the <code>originalHex</code> by <code>rotationDirection</code> degrees.
     *
     * @param originalHex center hex
     * @param targetHex hex to rotate
     * @param rotationDirection direction of the rotation
     *
     * @return result
     */
    Optional<Hexagon<T>> rotateHexagon(Hexagon<T> originalHex, Hexagon<T> targetHex, RotationDirection rotationDirection);


    /**
     * Returns the {@link Set} of {@link Hexagon}s which are <code>radius</code> distance
     * from <code>centerHexagon</code>.
     *
     * @param centerHexagon center
     * @param radius radius
     *
     * @return Set of hexagons or empty set if not applicable
     */
    Set<Hexagon<T>> calculateRingFrom(Hexagon<T> centerHexagon, int radius);

    /**
     * Returns a {@link List} of {@link Hexagon}s which must be traversed in the
     * given order to go from the <code>from</code> Hexagon to the <code>to</code> Hexagon.
     *
     * @param from starting hexagon
     * @param to target hexagon
     *
     * @return List of hexagons containing the line
     */
    List<Hexagon<T>> drawLine(Hexagon<T> from, Hexagon<T> to);

    /**
     * Returns true if the <code>from</code> {@link Hexagon} is visible from the <code>to</code> Hexagon.
     *
     * @param from the Hexagon that we are testing the visibility from
     * @param to the Hexagon from which we are testing the visibility to
     *
     * @return true if hexagon is visible, false otherwise
     */
    boolean isVisible(Hexagon<T> from, Hexagon<T> to);
}
