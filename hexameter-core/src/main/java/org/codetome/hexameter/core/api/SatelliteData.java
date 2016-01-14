package org.codetome.hexameter.core.api;

import java.io.Serializable;

/**
 * Represents arbitrary data which can be attached to a Hexagon.
 */
public interface SatelliteData extends Serializable {

    /**
     * Tells whether the Hexagon can be passed over when moving around the map or not.
     *
     * @return
     */
    boolean isPassable();

    /**
     * Sets whether the Hexagon is passable or not.
     */
    void setPassable(boolean passable);

    /**
     * Returns the movement cost when moving over the Hexagon.
     * @return
     */
    double getMovementCost();

    /**
     * Sets the movement cost.
     */
    void setMovementCost(double movementCost);

}
