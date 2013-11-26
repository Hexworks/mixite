package biz.pavonis.hexameter.internal.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.concurrent.atomic.AtomicReference;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.Point;
import biz.pavonis.hexameter.internal.SharedHexagonData;

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
    private AtomicReference<Object> satelliteData;

    public HexagonImpl(SharedHexagonData sharedHexagonData, int gridX, int gridZ) {
        this.sharedHexagonData = sharedHexagonData;
        this.satelliteData = new AtomicReference<Object>();
        double height = sharedHexagonData.getHeight();
        double width = sharedHexagonData.getWidth();
        double radius = sharedHexagonData.getRadius();
        this.gridX = gridX;
        this.gridZ = gridZ;
        if (HexagonOrientation.FLAT_TOP.equals(sharedHexagonData
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
        return "Hex#{x=" + gridX + ", z=" + gridZ + "}";
    }

    public final Point[] getPoints() {
        Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2
                    * Math.PI
                    / 6
                    * (i + sharedHexagonData.getOrientation()
                            .getCoordinateOffset());
            double x = centerX + sharedHexagonData.getRadius() * cos(angle);
            double y = centerY + sharedHexagonData.getRadius() * sin(angle);
            points[i] = new Point(x, y);
        }
        return points;
    }

    @SuppressWarnings("unchecked")
    public final <T> T getSatelliteData() {
        return (T) satelliteData.get();
    }

    public final <T> void setSatelliteData(T satelliteData) {
        this.satelliteData.set(satelliteData);
    }

    public final int getGridX() {
        return gridX;
    }

    public final int getGridY() {
        return -(gridX + gridZ);
    }

    public final int getGridZ() {
        return gridZ;
    }

    public final double getCenterX() {
        return centerX;
    }

    public final double getCenterY() {
        return centerY;
    }

}
