package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.Point.fromPosition;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
public class HexagonImpl<T extends SatelliteData> implements Hexagon<T> {

    private final CubeCoordinate coordinate;
    private final transient GridData sharedData;
    private final transient HexagonDataStorage<T> hexagonDataStorage;

    /**
     * Creates a new {@link Hexagon} object from shared data and a coordinate.
     *
     * @param gridData grid data
     * @param coordinate coordinate
     * @param hexagonDataStorage data map
     **/
    public HexagonImpl(final GridData gridData, final CubeCoordinate coordinate, HexagonDataStorage<T> hexagonDataStorage) {
        this.sharedData = gridData;
        this.coordinate = coordinate;
        this.hexagonDataStorage = hexagonDataStorage;
    }

    @Override
    public String getId() {
        return coordinate.toAxialKey();
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
    public CubeCoordinate getCubeCoordinate() {
        return coordinate;
    }

    @Override
    public int getGridX() {
        return coordinate.getGridX();
    }

    @Override
    public final int getGridY() {
        return coordinate.getGridY();
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
    public final Optional<T> getSatelliteData() {
        return hexagonDataStorage.getSatelliteDataBy(getCubeCoordinate());
    }

    @Override
    public final void setSatelliteData(final T satelliteData) {
        this.hexagonDataStorage.addCoordinate(getCubeCoordinate(), satelliteData);
    }

    @Override
    public void clearSatelliteData() {
        this.hexagonDataStorage.clearDataFor(getCubeCoordinate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        HexagonImpl hexagon = (HexagonImpl) object;
        return Objects.equals(coordinate, hexagon.coordinate);
    }
}
