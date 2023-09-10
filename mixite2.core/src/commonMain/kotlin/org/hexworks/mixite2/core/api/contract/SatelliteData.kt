package org.hexworks.mixite2.core.api.contract

/**
 * Represents arbitrary data which can be attached to a Hexagon.
 * An implementation should contain a set of fields for advanced
 * grid algorithms like pathfinding.
 */
interface SatelliteData {

    /**
     * Tells whether the Hexagon can be passed over when moving around the map or not.
     *
     * @return is passable?
     */
    /**
     * Sets whether the Hexagon is passable or not.
     *
     * @param passable passable?
     */
    var isPassable: Boolean

    /**
     * @return true if the [Hexagon] can bee seen through, false otherwise.
     */
    /**
     * Set whether the [Hexagon] can be seen through.
     *
     * @param opaque is opaque?
     */
    var isOpaque: Boolean

    /**
     * Returns the movement cost when moving over the Hexagon.
     *
     * @return movement cost
     */
    /**
     * Sets the movement cost.
     *
     * @param movementCost movement cost
     */
    var movementCost: Double

}
