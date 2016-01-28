package org.codetome.hexameter.core.api;

import java.util.Optional;

/**
 * Represents arbitrary data which can be attached to a Hexagon.
 * An implementation should contain a set of fields for advanced
 * grid algorithms like pathfinding.
 */
public interface SatelliteData {

    /**
     * Tells whether the Hexagon can be passed over when moving around the map or not.
     */
    boolean isPassable();

    /**
     * Sets whether the Hexagon is passable or not.
     */
    void setPassable(boolean passable);

    /**
     * Returns the movement cost when moving over the Hexagon.
     */
    double getMovementCost();

    /**
     * Sets the movement cost.
     */
    void setMovementCost(double movementCost);

    /**
     * Adds custom data to this SatelliteData.
     * @param key a key to be used for later retrieval
     * @param data the data itself
     * @param <T> type of the data
     */
    <T> void addCustomData(String key, T data);

    /**
     * Retrieves custom data stored in this SatelliteData.
     * @param key the key which can be used to fetch the data
     * @param <T> type of the data
     * @return data itself
     */
    <T> Optional<T> getCustomData(String key);


}
