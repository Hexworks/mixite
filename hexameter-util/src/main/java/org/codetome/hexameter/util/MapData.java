package org.codetome.hexameter.util;

import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Value class representing a map.
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTilesetUrl() {
        return tilesetUrl;
    }

    public void setTilesetUrl(String tilesetUrl) {
        this.tilesetUrl = tilesetUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public HexagonOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(HexagonOrientation orientation) {
        this.orientation = orientation;
    }

    public HexagonalGridLayout getLayout() {
        return layout;
    }

    public void setLayout(HexagonalGridLayout layout) {
        this.layout = layout;
    }

    public List<String[]> getCells() {
        return cells;
    }

    public void addCellData(String[] cellData) {
        cells.add(cellData);
    }
}
