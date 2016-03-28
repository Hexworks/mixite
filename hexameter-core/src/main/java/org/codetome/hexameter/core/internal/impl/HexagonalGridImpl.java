package org.codetome.hexameter.core.internal.impl;

import lombok.Getter;
import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.CoordinateConverter;
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

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.Point.fromPosition;
import static org.codetome.hexameter.core.internal.impl.HexagonImpl.newHexagon;

@Getter
@SuppressWarnings("PMD.UnusedPrivateField")
public final class HexagonalGridImpl implements HexagonalGrid {

    private static final int[][] NEIGHBORS = {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}};
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridData gridData;
    private final Map<AxialCoordinate, Object> hexagonStorage;
    private final Set<AxialCoordinate> coordinates;

    /**
     * Creates a new HexagonalGrid based on the provided HexagonalGridBuilder.
     *
     * @param builder builder
     */
    public HexagonalGridImpl(final HexagonalGridBuilder builder) {
        this.gridData = builder.getGridData();
        this.hexagonStorage = builder.getCustomStorage();
        this.coordinates = new LinkedHashSet<>();
        builder.getGridLayoutStrategy().fetchGridCoordinates(builder).subscribe(new Action1<AxialCoordinate>() {
            @Override
            public void call(AxialCoordinate axialCoordinate) {
                HexagonalGridImpl.this.coordinates.add(axialCoordinate);
            }
        });
    }

    @Override
    public Observable<Hexagon> getHexagons() {
        Observable<Hexagon> result = Observable.create(new OnSubscribe<Hexagon>() {
            @Override
            public void call(Subscriber<? super Hexagon> subscriber) {
                final Iterator<AxialCoordinate> coordinateIterator = coordinates.iterator();
                while (coordinateIterator.hasNext()) {
                    subscriber.onNext(newHexagon(gridData, coordinateIterator.next(), hexagonStorage));
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public Observable<Hexagon> getHexagonsByAxialRange(final AxialCoordinate from, final AxialCoordinate to) {
        Observable<Hexagon> result = Observable.create(new OnSubscribe<Hexagon>() {
            @Override
            public void call(Subscriber<? super Hexagon> subscriber) {
                for (int gridZ = from.getGridZ(); gridZ <= to.getGridZ(); gridZ++) {
                    for (int gridX = from.getGridX(); gridX <= to.getGridX(); gridX++) {
                        final AxialCoordinate currentCoordinate = fromCoordinates(gridX, gridZ);
                        if (containsAxialCoordinate(currentCoordinate)) {
                            subscriber.onNext(getByAxialCoordinate(currentCoordinate).get());
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
                        final int axialX = CoordinateConverter.convertOffsetCoordinatesToAxialX(gridX, gridY, gridData.getOrientation());
                        final int axialZ = CoordinateConverter.convertOffsetCoordinatesToAxialZ(gridX, gridY, gridData.getOrientation());
                        final AxialCoordinate axialCoordinate = fromCoordinates(axialX, axialZ);
                        final Optional<Hexagon> hex = getByAxialCoordinate(axialCoordinate);
                        if (hex.isPresent()) {
                            subscriber.onNext(hex.get());
                        }
                    }
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public boolean containsAxialCoordinate(final AxialCoordinate coordinate) {
        return this.coordinates.contains(coordinate);
    }

    @Override
    public Optional<Hexagon> getByAxialCoordinate(final AxialCoordinate coordinate) {

        return containsAxialCoordinate(coordinate)
                ? Optional.of(newHexagon(gridData, coordinate, hexagonStorage))
                : Optional.<Hexagon>empty();
    }

    @Override
    public Optional<Hexagon> getByPixelCoordinate(final double coordinateX, final double coordinateY) {
        int estimatedGridX = (int) (coordinateX / gridData.getHexagonWidth());
        int estimatedGridZ = (int) (coordinateY / gridData.getHexagonHeight());
        estimatedGridX = CoordinateConverter.convertOffsetCoordinatesToAxialX(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        estimatedGridZ = CoordinateConverter.convertOffsetCoordinatesToAxialZ(estimatedGridX, estimatedGridZ, gridData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        final AxialCoordinate estimatedCoordinate = fromCoordinates(estimatedGridX, estimatedGridZ);
        final Hexagon tempHex = newHexagon(gridData, estimatedCoordinate, hexagonStorage);

        Hexagon trueHex = refineHexagonByPixel(tempHex, fromPosition(coordinateX, coordinateY));

        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByAxialCoordinate(estimatedCoordinate);
        } else {
            return containsAxialCoordinate(trueHex.getAxialCoordinate()) ? Optional.of(trueHex) : Optional.<Hexagon>empty();
        }
    }

    @Override
    public Collection<Hexagon> getNeighborsOf(final Hexagon hexagon) {
        final Set<Hexagon> neighbors = new HashSet<>();
        for (final int[] neighbor : NEIGHBORS) {
            Hexagon retHex;
            final int neighborGridX = hexagon.getGridX() + neighbor[NEIGHBOR_X_INDEX];
            final int neighborGridZ = hexagon.getGridZ() + neighbor[NEIGHBOR_Z_INDEX];
            final AxialCoordinate neighborCoordinate = fromCoordinates(neighborGridX, neighborGridZ);
            if (containsAxialCoordinate(neighborCoordinate)) {
                retHex = getByAxialCoordinate(neighborCoordinate).get();
                neighbors.add(retHex);
            }
        }
        return neighbors;
    }

    @Override
    public void clearSatelliteData() {
        hexagonStorage.clear();
    }

    @Override
    public GridData getGridData() {
        return gridData;
    }

    private static boolean hexagonsAreAtTheSamePosition(final Hexagon hex0, final Hexagon hex1) {
        return hex0.getGridX() == hex1.getGridX() && hex0.getGridZ() == hex1.getGridZ();
    }

    private Hexagon refineHexagonByPixel(final Hexagon hexagon, final Point clickedPoint) {
        Hexagon refined = hexagon;
        double smallestDistance = clickedPoint.distanceFrom(fromPosition(refined.getCenterX(), refined.getCenterY()));
        for (final Hexagon neighbor : getNeighborsOf(hexagon)) {
            final double currentDistance = clickedPoint.distanceFrom(fromPosition(neighbor.getCenterX(), neighbor.getCenterY()));
            if (currentDistance < smallestDistance) {
                refined = neighbor;
                smallestDistance = currentDistance;
            }
        }
        return refined;
    }
}
