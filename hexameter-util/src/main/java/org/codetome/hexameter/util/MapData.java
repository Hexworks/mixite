package org.codetome.hexameter.util;

import lombok.Getter;
import lombok.Setter;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Value class representing a map.
 */
@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
public class MapData {

    private String name;
    private String tilesetUrl;
    private String version;
    private int width;
    private int height;
    private double radius;
    private HexagonOrientation orientation;
    private HexagonalGridLayout layout;
    private List<String[]> cells = new ArrayList<>();

    public void addCellData(String[] cellData) {
        cells.add(cellData);
    }
}
