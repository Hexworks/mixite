package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.*;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
public final class HexagonImpl<T extends SatelliteData> implements Hexagon<T> {
    private final CubeCoordinate coordinate;
    private final transient List<Double> vertices;
    private final transient List<Point> points;
    private final transient Rectangle externalBoundingBox;
    private final transient Rectangle internalBoundingBox;
    private final transient GridData sharedData;
    private final transient HexagonDataStorage<T> hexagonDataStorage;

    /**
     * Creates a new {@link Hexagon} object from shared data and a coordinate.
     *
     * @param gridData           grid data
     * @param coordinate         coordinate
     * @param hexagonDataStorage data map
     **/
    HexagonImpl(final GridData gridData, final CubeCoordinate coordinate, final HexagonDataStorage<T> hexagonDataStorage) {
        this.sharedData = gridData;
        this.coordinate = coordinate;
        this.hexagonDataStorage = hexagonDataStorage;

        this.points = calculatePoints();
        final int x1 = (int) points.get(3).getCoordinateX();
        final int y1 = (int) points.get(2).getCoordinateY();
        final int x2 = (int) points.get(0).getCoordinateX();
        final int y2 = (int) points.get(5).getCoordinateY();

        externalBoundingBox = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        internalBoundingBox = new Rectangle((int) (getCenterX() - (1.25 * sharedData.getRadius() / 2)),
                (int) (getCenterY() - (1.25 * sharedData.getRadius() / 2)),
                (int) (1.25f * sharedData.getRadius()),
                (int) (1.25f * sharedData.getRadius()));

        this.vertices = new ArrayList<>();
        for(Point point : points) {
            vertices.add(point.getCoordinateX());
            vertices.add(point.getCoordinateY());
        }
    }

    private List<Point> calculatePoints() {
        final List<Point> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final double angle = 2 * Math.PI / 6 * (i + sharedData.getOrientation().getCoordinateOffset());
            final double x = getCenterX() + sharedData.getRadius() * cos(angle);
            final double y = getCenterY() + sharedData.getRadius() * sin(angle);
            points.add(Point.fromPosition(x, y));
        }
        return points;
    }

    @Override
    public String getId() {
        return coordinate.toAxialKey();
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }

    @Override
    public List<Double> getVertices() {
        return vertices;
    }

    @Override
    public Rectangle getExternalBoundingBox() {
        return externalBoundingBox;
    }

    @Override
    public Rectangle getInternalBoundingBox() {
        return internalBoundingBox;
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
    public int getGridY() {
        return coordinate.getGridY();
    }

    @Override
    public int getGridZ() {
        return coordinate.getGridZ();
    }

    @Override
    public double getCenterX() {
        if (HexagonOrientation.FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridX() * sharedData.getHexagonWidth() + sharedData.getRadius();
        } else {
            return coordinate.getGridX() * sharedData.getHexagonWidth() + coordinate.getGridZ()
                    * sharedData.getHexagonWidth() / 2 + sharedData.getHexagonWidth() / 2;
        }
    }

    @Override
    public double getCenterY() {
        if (HexagonOrientation.FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + coordinate.getGridX()
                    * sharedData.getHexagonHeight() / 2 + sharedData.getHexagonHeight() / 2;
        } else {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + sharedData.getRadius();
        }
    }

    @Override
    public Optional<T> getSatelliteData() {
        return hexagonDataStorage.getSatelliteDataBy(getCubeCoordinate());
    }

    @Override
    public void setSatelliteData(final T satelliteData) {
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
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final HexagonImpl hexagon = (HexagonImpl) object;
        return Objects.equals(coordinate, hexagon.coordinate);
    }
}
