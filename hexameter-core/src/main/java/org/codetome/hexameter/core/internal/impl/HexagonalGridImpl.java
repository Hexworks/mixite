package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.internal.SharedHexagonData;
import org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayoutStrategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.Point.fromPosition;
import static org.codetome.hexameter.core.internal.impl.HexagonImpl.newHexagon;

public final class HexagonalGridImpl implements HexagonalGrid {

    private static final int[][] NEIGHBORS = {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}};
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridLayoutStrategy gridLayoutStrategy;
    private final HexagonalGridLayout gridLayout;
    private final SharedHexagonData sharedHexagonData;
    private final Map<AxialCoordinate, Object> hexagonStorage;
    private final Set<AxialCoordinate> coordinates;
    private final int gridWidth;
    private final int gridHeight;

    /**
     * Creates a new HexagonalGrid based on the provided HexagonalGridBuilder.
     */
    public HexagonalGridImpl(final HexagonalGridBuilder builder) {
        this.gridWidth = builder.getGridWidth();
        this.gridHeight = builder.getGridHeight();
        this.sharedHexagonData = builder.getSharedHexagonData();
        this.gridLayoutStrategy = builder.getGridLayoutStrategy();
        this.gridLayout = builder.getGridLayout();
        this.hexagonStorage = builder.getCustomStorage();
        this.coordinates = this.gridLayoutStrategy.fetchGridCoordinates(builder);
    }

    @Override
    public Iterable<Hexagon> getHexagons() {
        return coordinates.stream().map(coordinate -> newHexagon(sharedHexagonData, coordinate, hexagonStorage)).collect(Collectors.toList());
    }

    @Override
    public Iterable<Hexagon> getHexagonsByAxialRange(final AxialCoordinate from, final AxialCoordinate to) {
        return IntStream.rangeClosed(from.getGridX(), to.getGridX()).parallel()
                .mapToObj(x -> IntStream.rangeClosed(from.getGridZ(), to.getGridZ())
                        .mapToObj(z -> getByAxialCoordinate(fromCoordinates(x, z))))
                .flatMap(Stream::sequential)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Iterable<Hexagon> getHexagonsByOffsetRange(final int gridXFrom, final int gridXTo, final int gridYFrom, final int gridYTo) {
        return IntStream.rangeClosed(gridXFrom, gridXTo).parallel().mapToObj(x -> IntStream.rangeClosed(gridYFrom, gridYTo).mapToObj(y -> {
            final int axialX = CoordinateConverter.convertOffsetCoordinatesToAxialX(x, y, sharedHexagonData.getOrientation());
            final int axialZ = CoordinateConverter.convertOffsetCoordinatesToAxialZ(x, y, sharedHexagonData.getOrientation());
            final AxialCoordinate axialCoordinate = fromCoordinates(axialX, axialZ);
            return getByAxialCoordinate(axialCoordinate);
        })).flatMap(Stream::sequential).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }

    @Override
    public boolean containsAxialCoordinate(final AxialCoordinate coordinate) {
        return this.coordinates.contains(coordinate);
    }

    @Override
    public Optional<Hexagon> getByAxialCoordinate(final AxialCoordinate coordinate) {
        return containsAxialCoordinate(coordinate)
                ? Optional.of(newHexagon(sharedHexagonData, coordinate, hexagonStorage))
                : Optional.empty();
    }

    @Override
    public Optional<Hexagon> getByPixelCoordinate(final double coordinateX, final double coordinateY) {
        int estimatedGridX = (int) (coordinateX / sharedHexagonData.getWidth());
        int estimatedGridZ = (int) (coordinateY / sharedHexagonData.getHeight());
        estimatedGridX = CoordinateConverter.convertOffsetCoordinatesToAxialX(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
        estimatedGridZ = CoordinateConverter.convertOffsetCoordinatesToAxialZ(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        final AxialCoordinate estimatedCoordinate = fromCoordinates(estimatedGridX, estimatedGridZ);
        final Hexagon tempHex = newHexagon(sharedHexagonData, estimatedCoordinate, hexagonStorage);

        Hexagon trueHex = refineHexagonByPixel(tempHex, fromPosition(coordinateX, coordinateY));

        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByAxialCoordinate(estimatedCoordinate);
        } else {
            return containsAxialCoordinate(trueHex.getAxialCoordinate()) ? Optional.of(trueHex) : Optional.empty();
        }
    }

    @Override
    public Iterable<Hexagon> getNeighborsOf(final Hexagon hexagon) {
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

    public HexagonalGridLayout getGridLayout() {
        return gridLayout;
    }

    public SharedHexagonData getSharedHexagonData() {
        return sharedHexagonData;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    private boolean hexagonsAreAtTheSamePosition(final Hexagon hex0, final Hexagon hex1) {
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
