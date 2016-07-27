package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.backport.Optional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.codetome.hexameter.core.backport.Optional.ofNullable;


/**
 * Convenience class implementing SatelliteData.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class DefaultSatelliteData implements SatelliteData {

    private static final long serialVersionUID = 4397186040368615654L;
    private boolean passable;
    private boolean opaque;
    private double movementCost;
    private Map<String, Object> customData = new ConcurrentHashMap<>();

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

    @Override
    public <T> void addCustomData(final String key, final T data) {
        customData.put(key, data);
    }

    @Override
    public <T> Optional<T> getCustomData(final String key) {
        return ofNullable((T) customData.get(key));
    }
}
