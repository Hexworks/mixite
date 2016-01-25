package org.codetome.hexameter.util;

import org.codetome.hexameter.core.api.AbstractSatelliteData;

public class MapSatelliteData extends AbstractSatelliteData {

    private static final long serialVersionUID = -5144747914653122448L;

    private String tilesetId;

    public String getTilesetId() {
        return tilesetId;
    }

    public void setTilesetId(String tilesetId) {
        this.tilesetId = tilesetId;
    }
}
