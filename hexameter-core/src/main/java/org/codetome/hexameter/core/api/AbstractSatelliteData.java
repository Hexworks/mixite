package org.codetome.hexameter.core.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Convenience class implementing SatelliteData.
 */
@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
public abstract class AbstractSatelliteData implements SatelliteData {

    private boolean passable;
    private double movementCost;
}
