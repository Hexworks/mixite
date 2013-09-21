package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.Map;

class RhombusGridLayoutStrategy implements GridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		for (int y = 0; y < builder.getGridHeight(); y++) {
			for (int x = 0; x < builder.getGridWidth(); x++) {
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), x, y);
				hexagons.put(createKeyFromCoordinate(x, y), hexagon);
			}
		}
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return true;
	}

}
