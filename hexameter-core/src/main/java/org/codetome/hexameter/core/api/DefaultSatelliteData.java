package org.codetome.hexameter.core.api;

import static org.codetome.hexameter.core.backport.Optional.ofNullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codetome.hexameter.core.backport.Optional;

import lombok.Getter;
import lombok.Setter;


/**
 * Convenience class implementing SatelliteData.
 */
@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
public class DefaultSatelliteData implements SatelliteData {

    private static final long serialVersionUID = 4397186040368615654L;
    private boolean passable;
    private double movementCost;
    private Map<String, Object> customData = new ConcurrentHashMap<>();

    @Override
    public <T> void addCustomData(final String key, final T data) {
        customData.put(key, data);
    }

    @Override
    public <T> Optional<T> getCustomData(final String key) {
        return ofNullable((T) customData.get(key));
    }
}
