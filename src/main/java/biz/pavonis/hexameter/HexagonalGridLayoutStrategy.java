package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;
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
		int gridSize = builder.getGridHeight();
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		int mid = (int) Math.floor(gridSize / 2);
		int startX = (int) round(gridSize / 4) + 1;
		int midStartX = startX - mid;
		// TODO: this whole mess is not OK but I'm too tired now. Fix it later.
		for (int y = 0; y < gridSize; y++) {
			int distanceFromMid = Math.abs(mid - y);
			int currStartX = max(midStartX, startX);
			for (int x = currStartX; x < currStartX + gridSize - distanceFromMid; x++) {
				int gridX = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? x + mid / 2 : x;
				int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - mid / 2 : y;
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
