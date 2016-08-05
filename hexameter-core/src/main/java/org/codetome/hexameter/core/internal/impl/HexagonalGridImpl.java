package org.codetome.hexameter.core.internal.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action1;

public final class HexagonalGridImpl<T extends SatelliteData> implements HexagonalGrid<T> {

    private static final int[][] NEIGHBORS = {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}};
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridData gridData;
    private final HexagonDataStorage<T> hexagonDataStorage;

    /**
     * Creates a new HexagonalGrid based on the provided HexagonalGridBuilder.
     *
     * @param builder builder
     */
    public HexagonalGridImpl(final HexagonalGridBuilder<T> builder) {
        this.gridData = builder.getGridData();
        this.hexagonDataStorage = builder.getHexagonDataStorage();
        builder.getGridLayoutStrategy().fetchGridCoordinates(builder).subscribe(new Action1<CubeCoordinate>() {
            @Override
            public void call(final CubeCoordinate cubeCoordinate) {
                HexagonalGridImpl.this.hexagonDataStorage.addCoordinate(cubeCoordinate);
            }
        });
    }

    @Override
    public GridData getGridData() {
        return gridData;
    }

    @Override
    public Observable<Hexagon<T>> getHexagons() {
        return Observable.create(new OnSubscribe<Hexagon<T>>() {
            @Override
            public void call(final Subscriber<? super Hexagon<T>> subscriber) {
                hexagonDataStorage.getCoordinates().subscribe(new Subscriber<CubeCoordinate>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(final Throwable throwable) {
                        System.err.println(String.format("Cannot get Hexagons: <%s>", throwable.getMessage()));
                    }

                    @Override
                    public void onNext(final CubeCoordinate cubeCoordinate) {
                        subscriber.onNext(new HexagonImpl<>(gridData, cubeCoordinate, hexagonDataStorage));
                    }
                });
            }
        });
    }

    @Override
    public Observable<Hexagon<T>> getHexagonsByCubeRange(final CubeCoordinate from, final CubeCoordinate to) {
        return Observable.create(new OnSubscribe<Hexagon<T>>() {
            @Override
            public void call(final Subscriber<? super Hexagon<T>> subscriber) {
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
    }

    @Override
    public Observable<Hexagon<T>> getHexagonsByOffsetRange(final int gridXFrom, final int gridXTo, final int gridYFrom, final int gridYTo) {
        return Observable.create(new OnSubscribe<Hexagon<T>>() {
            @Override
            public void call(final Subscriber<? super Hexagon<T>> subscriber) {
                for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
                    for (int gridY = gridYFrom; gridY <= gridYTo; gridY++) {
                        final int cubeX = CoordinateConverter.convertOffsetCoordinatesToCubeX(gridX, gridY, gridData.getOrientation());
                        final int cubeZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(gridX, gridY, gridData.getOrientation());
                        final CubeCoordinate cubeCoordinate = CubeCoordinate.fromCoordinates(cubeX, cubeZ);
                        final Optional<Hexagon<T>> hexagon = getByCubeCoordinate(cubeCoordinate);
                        if (hexagon.isPresent()) {
                            subscriber.onNext(hexagon.get());
                        }
                    }
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public boolean containsCubeCoordinate(final CubeCoordinate cubeCoordinate) {
        return this.hexagonDataStorage.containsCoordinate(cubeCoordinate);
    }

    @Override
    public Optional<Hexagon<T>> getByCubeCoordinate(final CubeCoordinate coordinate) {
        return containsCubeCoordinate(coordinate)
                ? Optional.<Hexagon<T>>of(new HexagonImpl<>(gridData, coordinate, hexagonDataStorage))
                : Optional.<Hexagon<T>>empty();
    }

    @Override
    public Optional<Hexagon<T>> getByPixelCoordinate(final double coordinateX, final double coordinateY) {
        int estimatedGridX = (int) (coordinateX / gridData.getHexagonWidth());
        int estimatedGridZ = (int) (coordinateY / gridData.getHexagonHeight());
        estimatedGridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        estimatedGridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        final CubeCoordinate estimatedCoordinate = CubeCoordinate.fromCoordinates(estimatedGridX, estimatedGridZ);
        final Hexagon tempHex = new HexagonImpl<>(gridData, estimatedCoordinate, hexagonDataStorage);
        final Hexagon trueHex = refineHexagonByPixel(tempHex, Point.fromPosition(coordinateX, coordinateY));
        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByCubeCoordinate(estimatedCoordinate);
        } else {
            return containsCubeCoordinate(trueHex.getCubeCoordinate()) ? Optional.<Hexagon<T>>of(trueHex) : Optional.<Hexagon<T>>empty();
        }
    }

    @Override
    public Optional<Hexagon<T>> getNeighborByIndex(final Hexagon<T> hexagon, final int index) {
        final int neighborGridX = hexagon.getGridX() + NEIGHBORS[index][NEIGHBOR_X_INDEX];
        final int neighborGridZ = hexagon.getGridZ() + NEIGHBORS[index][NEIGHBOR_Z_INDEX];
        final CubeCoordinate neighborCoordinate = CubeCoordinate.fromCoordinates(neighborGridX, neighborGridZ);
        return getByCubeCoordinate(neighborCoordinate);
    }

    @Override
    public Collection<Hexagon<T>> getNeighborsOf(final Hexagon<T> hexagon) {
        final Set<Hexagon<T>> neighbors = new HashSet<>();
        for (int i = 0; i < NEIGHBORS.length; i++) {
            final Optional<Hexagon<T>> retHex = getNeighborByIndex(hexagon, i);
            if (retHex.isPresent()) {
                neighbors.add(retHex.get());
            }
        }
        return neighbors;
    }

    private boolean hexagonsAreAtTheSamePosition(final Hexagon<T> hex0, final Hexagon<T> hex1) {
        return hex0.getGridX() == hex1.getGridX() && hex0.getGridZ() == hex1.getGridZ();
    }

    private Hexagon<T> refineHexagonByPixel(final Hexagon<T> hexagon, final Point clickedPoint) {
        Hexagon refined = hexagon;
        double smallestDistance = clickedPoint.distanceFrom(Point.fromPosition(refined.getCenterX(), refined.getCenterY()));
        for (final Hexagon<T> neighbor : getNeighborsOf(hexagon)) {
            final double currentDistance = clickedPoint.distanceFrom(Point.fromPosition(neighbor.getCenterX(), neighbor.getCenterY()));
            if (currentDistance < smallestDistance) {
                refined = neighbor;
                smallestDistance = currentDistance;
            }
        }
        return refined;
    }
}
