package org.codetome.hexameter.core.api;

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
}
