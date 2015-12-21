package org.codetome.hexameter.internal.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;

import java.util.concurrent.atomic.AtomicReference;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.internal.SharedHexagonData;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
public class HexagonImpl implements Hexagon {

    private static final long serialVersionUID = -6658255569921274603L;
    private final SharedHexagonData sharedHexagonData;
    private final double centerX;
    private final double centerY;
    private final int gridX;
    private final int gridZ;
    private final AtomicReference<Object> satelliteData;

    public HexagonImpl(final SharedHexagonData sharedHexagonData, final int gridX, final int gridZ) {
        this.sharedHexagonData = sharedHexagonData;
        this.satelliteData = new AtomicReference<> ();
        final double height = sharedHexagonData.getHeight();
        final double width = sharedHexagonData.getWidth();
        final double radius = sharedHexagonData.getRadius();
        this.gridX = gridX;
        this.gridZ = gridZ;
        if (FLAT_TOP.equals(sharedHexagonData
                .getOrientation())) {
            centerX = gridX * width + radius;
            centerY = gridZ * height + gridX * height / 2 + height / 2;
        } else {
            centerX = gridX * width + gridZ * width / 2 + width / 2;
            centerY = gridZ * height + radius;
        }
    }

    @Override
    public String toString() {
        return "HexagonImpl#{x=" + gridX + ", z=" + gridZ + "}";
    }

    @Override
    public final Point[] getPoints() {
        final Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            final double angle = 2
                    * Math.PI
                    / 6
                    * (i + sharedHexagonData.getOrientation()
                            .getCoordinateOffset());
            final double x = centerX + sharedHexagonData.getRadius() * cos(angle);
            final double y = centerY + sharedHexagonData.getRadius() * sin(angle);
            points[i] = new Point(x, y);
        }
        return points;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> T getSatelliteData() {
        return (T) satelliteData.get();
    }

    @Override
    public final <T> void setSatelliteData(final T satelliteData) {
        this.satelliteData.set(satelliteData);
    }

    @Override
    public final int getGridX() {
        return gridX;
    }

    @Override
    public final int getGridY() {
        return -(gridX + gridZ);
    }

    @Override
    public final int getGridZ() {
        return gridZ;
    }

    @Override
    public final double getCenterX() {
        return centerX;
    }

    @Override
    public final double getCenterY() {
        return centerY;
    }

}
