package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
class RectangularGridLayoutStrategy implements GridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		for (int y = 0; y < builder.getGridHeight(); y++) {
			for (int x = 0; x < builder.getGridWidth(); x++) {
				int gridX = convertOffsetCoordinatesToAxialX(x, y, builder.getOrientation());
				int gridZ = convertOffsetCoordinatesToAxialZ(x, y, builder.getOrientation());
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), gridX, gridZ);
				hexagons.put(createKeyFromCoordinate(gridX, gridZ), hexagon);
			}
		}
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return true;
	}
}
