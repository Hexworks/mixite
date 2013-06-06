package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import biz.pavonis.hexameter.exception.HexagonNotFoundException;

class HexagonalGridImpl implements HexagonalGrid {

	private static final int[][] NEIGHBORS = { { +1, 0 }, { +1, -1 }, { 0, -1 }, { -1, 0 }, { -1, +1 }, { 0, +1 } };
	private static final int NEIGHBOR_X_INDEX = 0;
	private static final int NEIGHBOR_Z_INDEX = 1;
	private GridLayoutStrategy gridLayoutStrategy;
	private SharedHexagonData sharedHexagonData;
	private Map<String, Hexagon> hexagons;
	@SuppressWarnings("unused")
	private Logger logger;

	HexagonalGridImpl(HexagonalGridBuilder builder) {
		sharedHexagonData = builder.getSharedHexagonData();
		logger = Logger.getLogger(getClass().getName());
		gridLayoutStrategy = builder.getGridLayoutStrategy();
		hexagons = gridLayoutStrategy.createHexagons(builder);
	}

	public Map<String, Hexagon> getHexagons() {
		return hexagons;
	}

	public Hexagon getByGridCoordinate(int gridX, int gridZ) {
		checkCoordinate(gridX, gridZ);
		return hexagons.get(createKeyFromCoordinate(gridX, gridZ));
	}

	private void checkCoordinate(int gridX, int gridZ) {
		if (!hexagons.containsKey(createKeyFromCoordinate(gridX, gridZ))) {
			throw new HexagonNotFoundException("Coordinates are off the grid.");
		}
	}

	public Set<Hexagon> getNeighborsOf(Hexagon hexagon) {
		Set<Hexagon> neighbors = new HashSet<Hexagon>();
		for (int[] neighbor : NEIGHBORS) {
			Hexagon retHex = null;
			try {
				int neighborGridX = hexagon.getGridX() + neighbor[NEIGHBOR_X_INDEX];
				int neighborGridZ = hexagon.getGridZ() + neighbor[NEIGHBOR_Z_INDEX];
				retHex = getByGridCoordinate(neighborGridX, neighborGridZ);
				neighbors.add(retHex);
			} catch (HexagonNotFoundException e) {
				// logger.info("Neighbor was not found for: " + hexagon + ". It is off the grid."); // TODO: uncomment
			}
		}
		return neighbors;
	}

	public int calculateDistanceBetween(Hexagon hex0, Hexagon hex1) {
		double absX = abs(hex0.getGridX() - hex1.getGridX());
		double absY = abs(hex0.getGridY() - hex1.getGridY());
		double absZ = abs(hex0.getGridZ() - hex1.getGridZ());
		return (int) max(max(absX, absY), absZ);
	}

	public Set<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance) {
		// TODO: this is not optimal. Rewrite needed.
		Set<Hexagon> ret = new HashSet<Hexagon>();
		Set<Hexagon> currNeighbors = Collections.singleton(hexagon);
		for (int i = 0; i < distance; i++) {
			Set<Hexagon> newNeighbors = new HashSet<Hexagon>();
			for (Hexagon neighbor : currNeighbors) {
				newNeighbors.addAll(getNeighborsOf(neighbor));
			}
			newNeighbors.removeAll(currNeighbors);
			for (Hexagon neighbor : newNeighbors) {
				ret.add(neighbor);
			}
			currNeighbors = newNeighbors;
		}
		ret.remove(hexagon);
		return ret;
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
		for (String key : hexagons.keySet()) {
			hexagons.get(key).setSatelliteData(null);
		}
	}

	static String createKeyFromCoordinate(int gridX, int gridZ) {
		return gridX + "," + gridZ;
	}
}
