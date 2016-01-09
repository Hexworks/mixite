package org.codetome.hexameter.internal.impl;

import org.codetome.hexameter.api.*;
import org.codetome.hexameter.api.exception.HexagonNotFoundException;
import org.codetome.hexameter.internal.SharedHexagonData;
import org.codetome.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static org.codetome.hexameter.api.Point.fromPosition;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

public final class HexagonalGridImpl implements HexagonalGrid {

    private static final int[][] NEIGHBORS = {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}};
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridLayoutStrategy gridLayoutStrategy;
    private final SharedHexagonData sharedHexagonData;
    private final Map<String, Hexagon> hexagonStorage;

    public HexagonalGridImpl(final HexagonalGridBuilder builder) {
        sharedHexagonData = builder.getSharedHexagonData();
        gridLayoutStrategy = builder.getGridLayoutStrategy();
        hexagonStorage = builder.getCustomStorage();
        gridLayoutStrategy.createHexagons(builder).forEach(hex -> hexagonStorage.put(hex.getId(), hex));
    }

    @Override
    public Iterable<Hexagon> getHexagons() {
        return new HashSet<>(hexagonStorage.values());
    }

    @Override
    public Iterable<Hexagon> getHexagonsByAxialRange(final AxialCoordinate from, final AxialCoordinate to) {
        return IntStream.rangeClosed(from.getGridX(), to.getGridX()).parallel()
                .mapToObj(x -> IntStream.rangeClosed(from.getGridZ(), to.getGridZ())
                        .mapToObj(z -> getByAxialCoordinate(fromCoordinates(x, z))))
                .flatMap(Stream::sequential)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Iterable<Hexagon> getHexagonsByOffsetRange(final int gridXFrom, final int gridXTo, final int gridYFrom, final int gridYTo) {
        return IntStream.rangeClosed(gridXFrom, gridXTo).parallel().mapToObj(x -> IntStream.rangeClosed(gridYFrom, gridYTo).mapToObj(y -> {
            final int axialX = convertOffsetCoordinatesToAxialX(x, y, sharedHexagonData.getOrientation());
            final int axialZ = convertOffsetCoordinatesToAxialZ(x, y, sharedHexagonData.getOrientation());
            final AxialCoordinate axialCoordinate = fromCoordinates(axialX, axialZ);
            return getByAxialCoordinate(axialCoordinate);
        })).flatMap(Stream::sequential).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }

    @Override
    public Hexagon addHexagon(final AxialCoordinate coordinate) {
        return hexagonStorage.put(coordinate.toKey(), newHexagon(sharedHexagonData, coordinate));
    }

    @Override
    public Hexagon removeHexagon(final AxialCoordinate coordinate) {
        checkCoordinate(coordinate);
        return hexagonStorage.remove(coordinate.toKey());
    }

    @Override
    public boolean containsAxialCoordinate(final AxialCoordinate coordinate) {
        return hexagonStorage.containsKey(coordinate.toKey());
    }

    @Override
    public Optional<Hexagon> getByAxialCoordinate(final AxialCoordinate coordinate) {
        return containsAxialCoordinate(coordinate) ? of(hexagonStorage.get(coordinate.toKey())) : empty();
    }

    private void checkCoordinate(final AxialCoordinate coordinate) {
        if (!containsAxialCoordinate(coordinate)) {
            throw new HexagonNotFoundException("Coordinate is off the grid: " + coordinate.toKey());
        }
    }

    @Override
    public Optional<Hexagon> getByPixelCoordinate(final double x, final double y) {
        int estimatedGridX = (int) (x / sharedHexagonData.getWidth());
        int estimatedGridZ = (int) (y / sharedHexagonData.getHeight());
        estimatedGridX = convertOffsetCoordinatesToAxialX(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
        estimatedGridZ = convertOffsetCoordinatesToAxialZ(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        final AxialCoordinate estimatedCoordinate = fromCoordinates(estimatedGridX, estimatedGridZ);
        final Hexagon tempHex = newHexagon(sharedHexagonData, estimatedCoordinate);

        Hexagon trueHex = refineHexagonByPixel(tempHex, fromPosition(x, y));

        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByAxialCoordinate(estimatedCoordinate);
        } else {
            return containsAxialCoordinate(trueHex.getAxialCoordinate()) ? of(trueHex) : empty();
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

    @Override
    public void clearSatelliteData() {
        hexagonStorage.values().parallelStream().forEach(Hexagon::clearSatelliteData);
    }

}
