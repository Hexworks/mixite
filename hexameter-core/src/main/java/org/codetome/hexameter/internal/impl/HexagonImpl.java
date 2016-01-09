package org.codetome.hexameter.internal.impl;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.internal.SharedHexagonData;

import java.util.ArrayList;
import java.util.List;
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
    private final SharedHexagonData sharedData;
    private final AxialCoordinate coordinate;
    private final AtomicReference<Object> satelliteData;

    private HexagonImpl(final SharedHexagonData sharedHexagonData, final AxialCoordinate coordinate) {
        this.sharedData = sharedHexagonData;
        this.satelliteData = new AtomicReference<>();
        this.coordinate = coordinate;
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
    public final List<Point> getPoints() {
        final List<Point> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final double angle = 2 * Math.PI / 6 * (i + sharedData.getOrientation().getCoordinateOffset());
            final double x = getCenterX() + sharedData.getRadius() * cos(angle);
            final double y = getCenterY() + sharedData.getRadius() * sin(angle);
            points.add(fromPosition(x, y));
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
    public void clearSatelliteData() {
        setSatelliteData(null);
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
        if (FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridX() * sharedData.getWidth() + sharedData.getRadius();
        } else {
            return coordinate.getGridX() * sharedData.getWidth() + coordinate.getGridZ() * sharedData.getWidth() / 2 + sharedData.getWidth() / 2;
        }
    }

    @Override
    public final double getCenterY() {
        if (FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridZ() * sharedData.getHeight() + coordinate.getGridX() * sharedData.getHeight() / 2 + sharedData.getHeight() / 2;
        } else {
            return coordinate.getGridZ() * sharedData.getHeight() + sharedData.getRadius();
        }
    }

}
