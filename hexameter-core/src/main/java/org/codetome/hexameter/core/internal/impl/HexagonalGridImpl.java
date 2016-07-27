package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class HexagonalGridImpl implements HexagonalGrid {

    private static final int[][] NEIGHBORS = {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}};
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridData gridData;
    private final Map<CubeCoordinate, Object> hexagonStorage;
    private final Set<CubeCoordinate> coordinates;

    /**
     * Creates a new HexagonalGrid based on the provided HexagonalGridBuilder.
     *
     * @param builder builder
     */
    public HexagonalGridImpl(final HexagonalGridBuilder builder) {
        this.gridData = builder.getGridData();
        this.hexagonStorage = builder.getCustomStorage();
        this.coordinates = new LinkedHashSet<>();
        builder.getGridLayoutStrategy().fetchGridCoordinates(builder).subscribe(new Action1<CubeCoordinate>() {
            @Override
            public void call(CubeCoordinate cubeCoordinate) {
                HexagonalGridImpl.this.coordinates.add(cubeCoordinate);
            }
        });
    }

    private static boolean hexagonsAreAtTheSamePosition(final Hexagon hex0, final Hexagon hex1) {
        return hex0.getGridX() == hex1.getGridX() && hex0.getGridZ() == hex1.getGridZ();
    }

    @Override
    public GridData getGridData() {
        return gridData;
    }

    @Override
    public Observable<Hexagon> getHexagons() {
        Observable<Hexagon> result = Observable.create(new OnSubscribe<Hexagon>() {
            @Override
            public void call(Subscriber<? super Hexagon> subscriber) {
                final Iterator<CubeCoordinate> coordinateIterator = coordinates.iterator();
                while (coordinateIterator.hasNext()) {
                    subscriber.onNext(HexagonImpl.newHexagon(gridData, coordinateIterator.next(), hexagonStorage));
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public Observable<Hexagon> getHexagonsByCubeRange(final CubeCoordinate from, final CubeCoordinate to) {
        Observable<Hexagon> result = Observable.create(new OnSubscribe<Hexagon>() {
            @Override
            public void call(Subscriber<? super Hexagon> subscriber) {
                for (int gridZ = from.getGridZ(); gridZ <= to.getGridZ(); gridZ++) {
                    for (int gridX = from.getGridX(); gridX <= to.getGridX(); gridX++) {
                        final CubeCoordinate currentCoordinate = CubeCoordinate.fromCoordinates(gridX, gridZ);
                        if (containsCubeCoordinate(currentCoordinate)) {
                            subscriber.onNext(getByCubeCoordinate(currentCoordinate).get());
                        }
                    }
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public Observable<Hexagon> getHexagonsByOffsetRange(final int gridXFrom, final int gridXTo, final int gridYFrom, final int gridYTo) {
        Observable<Hexagon> result = Observable.create(new OnSubscribe<Hexagon>() {
            @Override
            public void call(Subscriber<? super Hexagon> subscriber) {
                for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
                    for (int gridY = gridYFrom; gridY <= gridYTo; gridY++) {
                        final int cubeX = CoordinateConverter.convertOffsetCoordinatesToCubeX(gridX, gridY, gridData.getOrientation());
                        final int cubeZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(gridX, gridY, gridData.getOrientation());
                        final CubeCoordinate cubeCoordinate = CubeCoordinate.fromCoordinates(cubeX, cubeZ);
                        final Optional<Hexagon> hexagon = getByCubeCoordinate(cubeCoordinate);
                        if (hexagon.isPresent()) {
                            subscriber.onNext(hexagon.get());
                        }
                    }
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public boolean containsCubeCoordinate(final CubeCoordinate coordinate) {
        return this.coordinates.contains(coordinate);
    }

    @Override
    public Optional<Hexagon> getByCubeCoordinate(final CubeCoordinate coordinate) {
        return containsCubeCoordinate(coordinate)
                ? Optional.of(HexagonImpl.newHexagon(gridData, coordinate, hexagonStorage))
                : Optional.<Hexagon>empty();
    }

    @Override
    public Optional<Hexagon> getByPixelCoordinate(final double coordinateX, final double coordinateY) {
        int estimatedGridX = (int) (coordinateX / gridData.getHexagonWidth());
        int estimatedGridZ = (int) (coordinateY / gridData.getHexagonHeight());
        estimatedGridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        estimatedGridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        final CubeCoordinate estimatedCoordinate = CubeCoordinate.fromCoordinates(estimatedGridX, estimatedGridZ);
        final Hexagon tempHex = HexagonImpl.newHexagon(gridData, estimatedCoordinate, hexagonStorage);

        Hexagon trueHex = refineHexagonByPixel(tempHex, Point.fromPosition(coordinateX, coordinateY));

        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByCubeCoordinate(estimatedCoordinate);
        } else {
            return containsCubeCoordinate(trueHex.getCubeCoordinate()) ? Optional.of(trueHex) : Optional.<Hexagon>empty();
        }
    }

    @Override
    public Optional<Hexagon> getNeighborByIndex(Hexagon hexagon, int index) {
        final int neighborGridX = hexagon.getGridX() + NEIGHBORS[index][NEIGHBOR_X_INDEX];
        final int neighborGridZ = hexagon.getGridZ() + NEIGHBORS[index][NEIGHBOR_Z_INDEX];
        final CubeCoordinate neighborCoordinate = CubeCoordinate.fromCoordinates(neighborGridX, neighborGridZ);
        return getByCubeCoordinate(neighborCoordinate);
    }

    @Override
    public Collection<Hexagon> getNeighborsOf(final Hexagon hexagon) {
        final Set<Hexagon> neighbors = new HashSet<>();
        for (int i = 0; i < NEIGHBORS.length; i++) {
            Optional<Hexagon> retHex = getNeighborByIndex(hexagon, i);
            if (retHex.isPresent()) {
                neighbors.add(retHex.get());
            }
        }
        return neighbors;
    }

    @Override
    public void clearSatelliteData() {
        hexagonStorage.clear();
    }

    private Hexagon refineHexagonByPixel(final Hexagon hexagon, final Point clickedPoint) {
        Hexagon refined = hexagon;
        double smallestDistance = clickedPoint.distanceFrom(Point.fromPosition(refined.getCenterX(), refined.getCenterY()));
        for (final Hexagon neighbor : getNeighborsOf(hexagon)) {
            final double currentDistance = clickedPoint.distanceFrom(Point.fromPosition(neighbor.getCenterX(), neighbor.getCenterY()));
            if (currentDistance < smallestDistance) {
                refined = neighbor;
                smallestDistance = currentDistance;
            }
        }
        return refined;
    }
}
