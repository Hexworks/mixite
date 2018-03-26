package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.*;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import java.util.*;

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
        for (CubeCoordinate cubeCoordinate : builder.getGridLayoutStrategy().fetchGridCoordinates(builder)) {
            HexagonalGridImpl.this.hexagonDataStorage.addCoordinate(cubeCoordinate);
        }
    }

    @Override
    public GridData getGridData() {
        return gridData;
    }

    @Override
    public Iterable<Hexagon<T>> getHexagons() {

        final Iterator<CubeCoordinate> coordIter = hexagonDataStorage.getCoordinates().iterator();

        return new Iterable<Hexagon<T>>() {
            @Override
            public Iterator<Hexagon<T>> iterator() {
                return new Iterator<Hexagon<T>>() {
                    @Override
                    public boolean hasNext() {
                        return coordIter.hasNext();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("This is not a mutable iterator.");
                    }

                    @Override
                    public Hexagon<T> next() {
                        return new HexagonImpl<>(gridData, coordIter.next(), hexagonDataStorage);
                    }
                };
            }
        };
    }

    @Override
    public Iterable<Hexagon<T>> getHexagonsByCubeRange(final CubeCoordinate from, final CubeCoordinate to) {
        final List<CubeCoordinate> coordinates = new ArrayList<>();

        for (int gridZ = from.getGridZ(); gridZ <= to.getGridZ(); gridZ++) {
            for (int gridX = from.getGridX(); gridX <= to.getGridX(); gridX++) {
                coordinates.add(CubeCoordinate.fromCoordinates(gridX, gridZ));
            }
        }

        final Iterator<CubeCoordinate> iter = coordinates.iterator();
        while (iter.hasNext()) {
            CubeCoordinate next = iter.next();
            if (!containsCubeCoordinate(next)) {
                iter.remove();
            }
        }

        final Iterator<CubeCoordinate> coordIter = coordinates.iterator();

        return new Iterable<Hexagon<T>>() {
            @Override
            public Iterator<Hexagon<T>> iterator() {
                return new Iterator<Hexagon<T>>() {
                    @Override
                    public boolean hasNext() {
                        return coordIter.hasNext();
                    }

                    @Override
                    public Hexagon<T> next() {
                        return getByCubeCoordinate(coordIter.next()).get();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("This is not a mutable iterator.");
                    }
                };
            }
        };
    }

    @Override
    public Iterable<Hexagon<T>> getHexagonsByOffsetRange(final int gridXFrom, final int gridXTo, final int gridYFrom, final int gridYTo) {
        final List<CubeCoordinate> coords = new ArrayList<>();

        for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
            for (int gridY = gridYFrom; gridY <= gridYTo; gridY++) {
                final int cubeX = CoordinateConverter.convertOffsetCoordinatesToCubeX(gridX, gridY, gridData.getOrientation());
                final int cubeZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(gridX, gridY, gridData.getOrientation());
                CubeCoordinate coord = CubeCoordinate.fromCoordinates(cubeX, cubeZ);
                if (getByCubeCoordinate(coord).isPresent()) {
                    coords.add(coord);
                }
            }
        }

        final Iterator<CubeCoordinate> coordIter = coords.iterator();

        return new Iterable<Hexagon<T>>() {
            @Override
            public Iterator<Hexagon<T>> iterator() {
                return new Iterator<Hexagon<T>>() {
                    @Override
                    public boolean hasNext() {
                        return coordIter.hasNext();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("This is not a mutable iterator.");
                    }

                    @Override
                    public Hexagon<T> next() {
                        return getByCubeCoordinate(coordIter.next()).get();
                    }
                };
            }
        };
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
