package org.codetome.hexameter.core.api;

/**
 * Convenience class implementing SatelliteData.
 */
public abstract class AbstractSatelliteData implements SatelliteData {

    private boolean passable;
    private double movementCost;

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    @Override
    public double getMovementCost() {
        return movementCost;
    }

    @Override
    public void setMovementCost(double movementCost) {
        this.movementCost = movementCost;
    }
}
