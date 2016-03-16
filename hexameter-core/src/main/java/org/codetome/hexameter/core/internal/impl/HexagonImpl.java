package org.codetome.hexameter.core.internal.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.Point.fromPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
@EqualsAndHashCode
@ToString(of = "coordinate")
public class HexagonImpl implements Hexagon {

    private final AxialCoordinate coordinate;
    private final transient GridData sharedData;
    private final transient Map<AxialCoordinate, Object> dataMap;

    private HexagonImpl(final GridData gridData, final AxialCoordinate coordinate, Map<AxialCoordinate, Object> dataMap) {
        this.sharedData = gridData;
        this.coordinate = coordinate;
        this.dataMap = dataMap;
    }

    /**
     * Creates a new {@link Hexagon} object from shared data and a coordinate.
     */
    public static Hexagon newHexagon(final GridData gridData, final AxialCoordinate coordinate, Map<AxialCoordinate, Object> dataMap) {
        return new HexagonImpl(gridData, coordinate, dataMap);
    }

    @Override
    public String getId() {
        return coordinate.toKey();
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
            return coordinate.getGridX() * sharedData.getHexagonWidth() + sharedData.getRadius();
        } else {
            return coordinate.getGridX() * sharedData.getHexagonWidth() + coordinate.getGridZ()
                    * sharedData.getHexagonWidth() / 2 + sharedData.getHexagonWidth() / 2;
        }
    }

    @Override
    public final double getCenterY() {
        if (FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + coordinate.getGridX()
                    * sharedData.getHexagonHeight() / 2 + sharedData.getHexagonHeight() / 2;
        } else {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + sharedData.getRadius();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T extends SatelliteData> Optional<T> getSatelliteData() {
        final Object result = dataMap.get(getAxialCoordinate());
        return result == null ? Optional.empty() : Optional.of((T) result);
    }

    @Override
    public final <T extends SatelliteData> void setSatelliteData(final T satelliteData) {
        this.dataMap.put(getAxialCoordinate(), satelliteData);
    }

    @Override
    public void clearSatelliteData() {
        this.dataMap.remove(getAxialCoordinate());
    }
}
