package org.codetome.hexameter.util;

import lombok.Getter;
import lombok.Setter;
import org.codetome.hexameter.core.api.DefaultSatelliteData;

@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
public class MapSatelliteData extends DefaultSatelliteData {

    private static final long serialVersionUID = -5144747914653122448L;

    private String tilesetId;
}
