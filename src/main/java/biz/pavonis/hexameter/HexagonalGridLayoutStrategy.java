package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.round;

import java.util.HashMap;
import java.util.Map;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
class HexagonalGridLayoutStrategy implements GridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		double gridSize = builder.getGridHeight();
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		int startX = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? (int) floor(gridSize / 2d) : (int) round(gridSize / 4d);
		int hexRadius = (int) floor(gridSize / 2d);
		int minX = startX - hexRadius;
		for (int y = 0; y < gridSize; y++) {
			int distanceFromMid = Math.abs(hexRadius - y);
			for (int x = max(startX, minX); x <= max(startX, minX) + hexRadius + hexRadius - distanceFromMid; x++) {
				int gridX = x;
				int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - (int) floor(gridSize / 4d) : y;
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), gridX, gridZ);
				hexagons.put(createKeyFromCoordinate(gridX, gridZ), hexagon);
			}
			startX--;
		}
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return gridHeight == gridWidth && gridHeight % 2 == 1;
	}

}
