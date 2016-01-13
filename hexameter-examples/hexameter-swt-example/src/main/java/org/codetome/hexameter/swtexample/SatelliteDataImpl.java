package org.codetome.hexameter.swtexample;

import org.codetome.hexameter.core.api.SatelliteData;

public class SatelliteDataImpl implements SatelliteData {

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
