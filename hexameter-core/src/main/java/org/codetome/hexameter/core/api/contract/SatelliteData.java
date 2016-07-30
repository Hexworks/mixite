package org.codetome.hexameter.core.api.contract;

import org.codetome.hexameter.core.api.Hexagon;

/**
 * Represents arbitrary data which can be attached to a Hexagon.
 * An implementation should contain a set of fields for advanced
 * grid algorithms like pathfinding.
 */
public interface SatelliteData {

    /**
     * Tells whether the Hexagon can be passed over when moving around the map or not.
     *
     * @return is passable?
     */
    boolean isPassable();

    /**
     * Sets whether the Hexagon is passable or not.
     *
     * @param passable passable?
     */
    void setPassable(boolean passable);

    /**
     * @return true if the {@link Hexagon} can bee seen through, false otherwise.
     */
    boolean isOpaque();

    /**
     * Set whether the {@link Hexagon} can be seen through.
     *
     * @param opaque is opaque?
     */
    void setOpaque(boolean opaque);

    /**
     * Returns the movement cost when moving over the Hexagon.
     *
     * @return movement cost
     */
    double getMovementCost();

    /**
     * Sets the movement cost.
     *
     * @param movementCost movement cost
     */
    void setMovementCost(double movementCost);

}
