package org.codetome.hexameter.internal.impl;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.internal.SharedHexagonData;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.api.Point.fromPosition;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
public class HexagonImpl implements Hexagon {

    private static final long serialVersionUID = -6658255569921274603L;
    private final SharedHexagonData sharedHexagonData;
    private final double centerX;
    private final double centerY;
    private final AxialCoordinate coordinate;
    private final AtomicReference<Object> satelliteData;

    private HexagonImpl(final SharedHexagonData sharedHexagonData, final AxialCoordinate coordinate) {
        this.sharedHexagonData = sharedHexagonData;
        this.satelliteData = new AtomicReference<>();
        final double height = sharedHexagonData.getHeight();
        final double width = sharedHexagonData.getWidth();
        final double radius = sharedHexagonData.getRadius();
        this.coordinate = coordinate;
        if (FLAT_TOP.equals(sharedHexagonData.getOrientation())) {
            centerX = coordinate.getGridX() * width + radius;
            centerY = coordinate.getGridZ() * height + coordinate.getGridX() * height / 2 + height / 2;
        } else {
            centerX = coordinate.getGridX() * width + coordinate.getGridZ() * width / 2 + width / 2;
            centerY = coordinate.getGridZ() * height + radius;
        }
    }

    /**
     * Creates a new {@link Hexagon} object from shared data and a coordinate.
     *
     * @param sharedHexagonData
     * @param coordinate
     *
     * @return
     */
    public static Hexagon newHexagon(final SharedHexagonData sharedHexagonData, final AxialCoordinate coordinate) {
        return new HexagonImpl(sharedHexagonData, coordinate);
    }

    @Override
    public String getId() {
        return coordinate.toKey();
    }

    @Override
    public String toString() {
        return "HexagonImpl#{x=" + coordinate.getGridX() + ", z=" + coordinate.getGridZ() + "}";
    }

    @Override
    public final Point[] getPoints() {
        final Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            final double angle = 2 * Math.PI / 6 * (i + sharedHexagonData.getOrientation().getCoordinateOffset());
            final double x = centerX + sharedHexagonData.getRadius() * cos(angle);
            final double y = centerY + sharedHexagonData.getRadius() * sin(angle);
            points[i] = fromPosition(x, y);
        }
        return points;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> Optional<T> getSatelliteData() {
        final Object result = satelliteData.get();
        return result == null ? empty() : of((T) result);
    }

    @Override
    public final <T> void setSatelliteData(final T satelliteData) {
        this.satelliteData.set(satelliteData);
    }

    @Override
    public AxialCoordinate getAxialCoordinate() {
        return coordinate;
    }

    @Override
    public int getGridX() {
        return coordinate.getGridX();
    }

    @Override
    public final int getGridY() {
        return -(coordinate.getGridX() + coordinate.getGridZ());
    }

    @Override
    public int getGridZ() {
        return coordinate.getGridZ();
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
