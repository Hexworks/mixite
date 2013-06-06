package biz.pavonis.hexameter;

import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static biz.pavonis.hexameter.HexagonalGridImpl.createKeyFromCoordinate;
import static java.lang.Math.floor;

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
		HexagonOrientation orientation = builder.getOrientation();
		int endIdx = 1;
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < endIdx; x++) {
				int gridX = convertOffsetCoordinatesToAxialX(x, y, orientation);
				int gridZ = convertOffsetCoordinatesToAxialZ(x, y, orientation);
				if (HexagonOrientation.POINTY_TOP.equals(orientation)) {
					if (gridSize % 2 == 1 && y % 2 == 1) {
						gridX--;
					}
					gridX += (int) floor((gridSize - y) / 2);
				}
				if (HexagonOrientation.FLAT_TOP.equals(orientation)) {
					if (x % 2 == 1) {
						gridZ--;
					}
					gridZ -= (int) floor(x / 2);
				}
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), gridX, gridZ);
				hexagons.put(createKeyFromCoordinate(gridX, gridZ), hexagon);
			}
			endIdx++;
		}
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return gridHeight == gridWidth;
	}
}
