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
    public final boolean isPassable() {
        return passable;
    }

    @Override
    public final void setPassable(final boolean passable) {
        this.passable = passable;
    }

    @Override
    public final boolean isOpaque() {
        return opaque;
    }

    @Override
    public final void setOpaque(final boolean opaque) {
        this.opaque = opaque;
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
