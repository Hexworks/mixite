package org.codetome.hexameter.core.api.defaults;

import org.codetome.hexameter.core.api.contract.SatelliteData;


/**
 * Convenience class implementing SatelliteData.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class DefaultSatelliteData implements SatelliteData {

    private boolean passable;
    private boolean opaque;
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
    public boolean isOpaque() {
        return opaque;
    }

    @Override
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
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
