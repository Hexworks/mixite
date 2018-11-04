package org.hexworks.mixite.swtexample;

import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData;

public class SatelliteDataImpl extends DefaultSatelliteData {

    private static final long serialVersionUID = 1335166038345783576L;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
