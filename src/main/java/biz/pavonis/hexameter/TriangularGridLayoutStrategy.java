package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
class TriangularGridLayoutStrategy implements GridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		int gridSize = builder.getGridHeight();
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		for (int y = 0; y < gridSize; y++) {
			int endX = gridSize - y;
			for (int x = 0; x < endX; x++) {
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), x, y);
				hexagons.put(createKeyFromCoordinate(x, y), hexagon);
			}
		}
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return gridHeight == gridWidth;
	}
}
