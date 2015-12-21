package org.codetome.hexameter.internal.impl;

import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static org.codetome.hexameter.api.CoordinateConverter.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.api.exception.HexagonNotFoundException;
import org.codetome.hexameter.internal.SharedHexagonData;
import org.codetome.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;

public final class HexagonalGridImpl implements HexagonalGrid {

    private static final int[][] NEIGHBORS = { { +1, 0 }, { +1, -1 },
            { 0, -1 }, { -1, 0 }, { -1, +1 }, { 0, +1 } };
    private static final int NEIGHBOR_X_INDEX = 0;
    private static final int NEIGHBOR_Z_INDEX = 1;

    private final GridLayoutStrategy gridLayoutStrategy;
    private final SharedHexagonData sharedHexagonData;
    private final Map<String, Hexagon> hexagonStorage;

    public HexagonalGridImpl(HexagonalGridBuilder builder) {
        sharedHexagonData = builder.getSharedHexagonData();
        gridLayoutStrategy = builder.getGridLayoutStrategy();
        if (builder.getCustomStorage() != null) {
            hexagonStorage = builder.getCustomStorage();
        } else {
            hexagonStorage = new ConcurrentHashMap<> (); // TODO:
                                                                       // to
                                                                       // factory
                                                                       // method
        }
        hexagonStorage.putAll(gridLayoutStrategy.createHexagons(builder));
    }

    public Map<String, Hexagon> getHexagons() {
        return hexagonStorage;
    }

    public Map<String, Hexagon> getHexagonsByAxialRange(int gridXFrom,
            int gridXTo, int gridZFrom, int gridZTo) {
        Map<String, Hexagon> range = new HashMap<> ();
        for (int gridZ = gridZFrom; gridZ <= gridZTo; gridZ++) {
            for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
                String key = createKeyFromCoordinate(gridX, gridZ);
                range.put(key, getByGridCoordinate(gridX, gridZ));
            }
        }
        return range;
    }

    public Map<String, Hexagon> getHexagonsByOffsetRange(int gridXFrom,
            int gridXTo, int gridYFrom, int gridYTo) {
        Map<String, Hexagon> range = new HashMap<> ();
        for (int gridY = gridYFrom; gridY <= gridYTo; gridY++) {
            for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
                int axialX = convertOffsetCoordinatesToAxialX(gridX, gridY,
                        sharedHexagonData.getOrientation());
                int axialZ = convertOffsetCoordinatesToAxialZ(gridX, gridY,
                        sharedHexagonData.getOrientation());
                String key = createKeyFromCoordinate(axialX, axialZ);
                range.put(key, getByGridCoordinate(axialX, axialZ));
            }
        }
        return range;
    }

    public Hexagon addHexagon(int gridX, int gridZ) {
        Hexagon newHex = new HexagonImpl(sharedHexagonData, gridX, gridZ);
        hexagonStorage.put(createKeyFromCoordinate(gridX, gridZ), newHex);
        return newHex;
    }

    public Hexagon removeHexagon(int gridX, int gridZ) {
        checkCoordinate(gridX, gridZ);
        return hexagonStorage.remove(createKeyFromCoordinate(gridX, gridZ));
    }

    public boolean containsCoordinate(int gridX, int gridZ) {
        return hexagonStorage
                .containsKey(createKeyFromCoordinate(gridX, gridZ));
    }

    public Hexagon getByGridCoordinate(int gridX, int gridZ) {
        checkCoordinate(gridX, gridZ);
        return hexagonStorage.get(createKeyFromCoordinate(gridX, gridZ));
    }

    private void checkCoordinate(int gridX, int gridZ) {
        if (!containsCoordinate(gridX, gridZ)) {
            throw new HexagonNotFoundException(
                    "Coordinates are off the grid: (x=" + gridX + ",z=" + gridZ
                            + ")");
        }
    }

    public Hexagon getByPixelCoordinate(double x, double y) {
        int estimatedGridX = (int) (x / sharedHexagonData.getWidth());
        int estimatedGridZ = (int) (y / sharedHexagonData.getHeight());
        estimatedGridX = convertOffsetCoordinatesToAxialX(estimatedGridX,
                estimatedGridZ, sharedHexagonData.getOrientation());
        estimatedGridZ = convertOffsetCoordinatesToAxialZ(estimatedGridX,
                estimatedGridZ, sharedHexagonData.getOrientation());
        // it is possible that the estimated coordinates are off the grid so we
        // create a virtual hexagon
        Hexagon tempHex = new HexagonImpl(sharedHexagonData, estimatedGridX,
                estimatedGridZ);
        Hexagon trueHex = refineHexagonByPixel(tempHex, x, y);
        if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
            return getByGridCoordinate(estimatedGridX, estimatedGridZ);
        } else {
            return trueHex;
        }
    }

    public Set<Hexagon> getNeighborsOf(Hexagon hexagon) {
        Set<Hexagon> neighbors = new HashSet<> ();
        for (int[] neighbor : NEIGHBORS) {
            Hexagon retHex = null;
            int neighborGridX = hexagon.getGridX() + neighbor[NEIGHBOR_X_INDEX];
            int neighborGridZ = hexagon.getGridZ() + neighbor[NEIGHBOR_Z_INDEX];
            if (containsCoordinate(neighborGridX, neighborGridZ)) {
                retHex = getByGridCoordinate(neighborGridX, neighborGridZ);
                neighbors.add(retHex);
            }
        }
        return neighbors;
    }

    private boolean hexagonsAreAtTheSamePosition(Hexagon hex0, Hexagon hex1) {
        return hex0.getGridX() == hex1.getGridX()
                && hex0.getGridZ() == hex1.getGridZ();
    }

    private Hexagon refineHexagonByPixel(Hexagon hexagon, double x, double y) {
        Hexagon refined = hexagon;
        Point clickedPoint = new Point(x, y);
        double smallestDistance = Point.distance(clickedPoint, new Point(
                refined.getCenterX(), refined.getCenterY()));
        for (Hexagon neighbor : getNeighborsOf(hexagon)) {
            double currentDistance = Point.distance(clickedPoint, new Point(
                    neighbor.getCenterX(), neighbor.getCenterY()));
            if (currentDistance < smallestDistance) {
                refined = neighbor;
                smallestDistance = currentDistance;
            }
        }
        return refined;
    }

    public void clearSatelliteData() {
        for (String key : hexagonStorage.keySet()) {
            hexagonStorage.get(key).setSatelliteData(null);
        }
    }

}
