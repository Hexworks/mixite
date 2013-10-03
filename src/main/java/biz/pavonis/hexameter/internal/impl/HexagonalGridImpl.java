package biz.pavonis.hexameter.internal.impl;

import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static biz.pavonis.hexameter.api.CoordinateConverter.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.api.Point;
import biz.pavonis.hexameter.api.exception.HexagonNotFoundException;
import biz.pavonis.hexameter.internal.SharedHexagonData;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;

public final class HexagonalGridImpl implements HexagonalGrid {

	private static final int[][] NEIGHBORS = { { +1, 0 }, { +1, -1 }, { 0, -1 }, { -1, 0 }, { -1, +1 }, { 0, +1 } };
	private static final int NEIGHBOR_X_INDEX = 0;
	private static final int NEIGHBOR_Z_INDEX = 1;

	private final Logger logger;
	private final GridLayoutStrategy gridLayoutStrategy;
	private final SharedHexagonData sharedHexagonData;
	private final Map<String, Hexagon> hexagonStorage;

	public HexagonalGridImpl(HexagonalGridBuilder builder) {
		sharedHexagonData = builder.getSharedHexagonData();
		logger = Logger.getLogger(getClass().getName());
		gridLayoutStrategy = builder.getGridLayoutStrategy();
		if (builder.getCustomStorage() != null) {
			hexagonStorage = builder.getCustomStorage();
		} else {
			hexagonStorage = new ConcurrentHashMap<String, Hexagon>();
		}
		hexagonStorage.putAll(gridLayoutStrategy.createHexagons(builder));
	}

	public Map<String, Hexagon> getHexagons() {
		return hexagonStorage;
	}

	public Map<String, Hexagon> getHexagonGridByAxialRange(int gridXFrom, int gridXTo, int gridZFrom, int gridZTo) {
		Map<String, Hexagon> range = new HashMap<String, Hexagon>();
		for (int gridZ = gridZFrom; gridZ <= gridZTo; gridZ++) {
			for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
				String key = createKeyFromCoordinate(gridX, gridZ);
				if (hexagonStorage.containsKey(key)) {
					range.put(key, hexagonStorage.get(key));
				}
			}
		}
		return range;
	}

	public Map<String, Hexagon> getHexagonGridByOffsetRange(int gridXFrom, int gridXTo, int gridYFrom, int gridYTo) {
		Map<String, Hexagon> range = new HashMap<String, Hexagon>();
		for (int gridY = gridYFrom; gridY <= gridYTo; gridY++) {
			for (int gridX = gridXFrom; gridX <= gridXTo; gridX++) {
				String key = createKeyFromCoordinate(convertOffsetCoordinatesToAxialX(gridX, gridY, sharedHexagonData.getOrientation()),
						convertOffsetCoordinatesToAxialZ(gridX, gridY, sharedHexagonData.getOrientation()));
				if (hexagonStorage.containsKey(key)) {
					range.put(key, hexagonStorage.get(key));
				}
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
		return hexagonStorage.remove(createKeyFromCoordinate(gridX, gridZ));
	}

	public boolean containsCoordinate(int gridX, int gridZ) {
		return hexagonStorage.containsKey(createKeyFromCoordinate(gridX, gridZ));
	}

	public Hexagon getByGridCoordinate(int gridX, int gridZ) {
		checkCoordinate(gridX, gridZ);
		return hexagonStorage.get(createKeyFromCoordinate(gridX, gridZ));
	}

	private void checkCoordinate(int gridX, int gridZ) {
		if (!hexagonStorage.containsKey(createKeyFromCoordinate(gridX, gridZ))) {
			throw new HexagonNotFoundException("Coordinates are off the grid.");
		}
	}

	public Hexagon getByPixelCoordinate(double x, double y) {
		int estimatedGridX = (int) (x / sharedHexagonData.getWidth());
		int estimatedGridZ = (int) (y / sharedHexagonData.getHeight());
		estimatedGridX = convertOffsetCoordinatesToAxialX(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
		estimatedGridZ = convertOffsetCoordinatesToAxialZ(estimatedGridX, estimatedGridZ, sharedHexagonData.getOrientation());
		// it is possible that the estimated coordinates are off the grid so we create a virtual hexagon
		Hexagon tempHex = new HexagonImpl(sharedHexagonData, estimatedGridX, estimatedGridZ);
		Hexagon trueHex = refineHexagonByPixel(tempHex, x, y);
		if (hexagonsAreAtTheSamePosition(tempHex, trueHex)) {
			return getByGridCoordinate(estimatedGridX, estimatedGridZ);
		} else {
			return trueHex;
		}
	}

	public Set<Hexagon> getNeighborsOf(Hexagon hexagon) {
		Set<Hexagon> neighbors = new HashSet<Hexagon>();
		for (int[] neighbor : NEIGHBORS) {
			Hexagon retHex = null;
			try {
				int neighborGridX = hexagon.getGridX() + neighbor[NEIGHBOR_X_INDEX];
				int neighborGridZ = hexagon.getGridZ() + neighbor[NEIGHBOR_Z_INDEX];
				if (containsCoordinate(neighborGridX, neighborGridZ)) {
					retHex = getByGridCoordinate(neighborGridX, neighborGridZ);
					neighbors.add(retHex);
				}
			} catch (HexagonNotFoundException e) {
				logger.info("Neighbor was not found for: " + hexagon + ". It is off the grid.");
			}
		}
		return neighbors;
	}

	private boolean hexagonsAreAtTheSamePosition(Hexagon hex0, Hexagon hex1) {
		return hex0.getCenterX() == hex1.getCenterX() && hex0.getCenterY() == hex1.getCenterY();
	}

	private Hexagon refineHexagonByPixel(Hexagon hexagon, double x, double y) {
		Hexagon refined = hexagon;
		Point clickedPoint = new Point(x, y);
		double smallestDistance = Point.distance(clickedPoint, new Point(refined.getCenterX(), refined.getCenterY()));
		for (Hexagon neighbor : getNeighborsOf(hexagon)) {
			double currentDistance = Point.distance(clickedPoint, new Point(neighbor.getCenterX(), neighbor.getCenterY()));
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
