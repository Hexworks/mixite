package org.codetome.hexameter.restexample.payload;

import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class HexagonBuilderPayload {
    private Integer id;
    private int gridWidth;
    private int gridHeight;
    private double radius;
    private HexagonOrientation orientation = POINTY_TOP;
    private HexagonalGridLayout gridLayout = RECTANGULAR;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(final int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(final int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public HexagonOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(final HexagonOrientation orientation) {
        this.orientation = orientation;
    }

    public HexagonalGridLayout getGridLayout() {
        return gridLayout;
    }

    public void setGridLayout(final HexagonalGridLayout gridLayout) {
        this.gridLayout = gridLayout;
    }
}
