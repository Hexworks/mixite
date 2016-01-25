package org.codetome.hexameter.core.api;

/**
 * Convenience class implementing SatelliteData.
 */
public abstract class AbstractSatelliteData implements SatelliteData {

    private boolean passable;
    private double movementCost;

    @Override
    public final boolean isPassable() {
        return passable;
    }

    @Override
    public final void setPassable(final boolean passable) {
        this.passable = passable;
    }

    @Override
    public final double getMovementCost() {
        return movementCost;
    }

    @Override
    public final void setMovementCost(final double movementCost) {
        this.movementCost = movementCost;
    }
}
