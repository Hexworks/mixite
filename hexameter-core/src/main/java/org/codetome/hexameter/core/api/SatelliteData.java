package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.backport.Optional;

/**
 * Represents arbitrary data which can be attached to a Hexagon.
 * An implementation should contain a set of fields for advanced
 * grid algorithms like pathfinding.
 */
public interface SatelliteData {

    /**
     * Tells whether the Hexagon can be passed over when moving around the map or not.
     * @return is passable?
     */
    boolean isPassable();

    /**
     * Sets whether the Hexagon is passable or not.
     * @param passable passable?
     */
    void setPassable(boolean passable);

    /**
     * Returns the movement cost when moving over the Hexagon.
     * @return movement cost
     */
    double getMovementCost();

    /**
     * Sets the movement cost.
     * @param movementCost movement cost
     */
    void setMovementCost(double movementCost);
    
    /**
     * @return true if the hexagon blocks view, false otherwise.
     */
    boolean isBlocksView();
    
    /**
     * Set whether the hexagon blocks view.
     * @param blocksView pass true if the hexagon blocks view, false otherwise
     */
    void setBlocksView(boolean blocksView);

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
