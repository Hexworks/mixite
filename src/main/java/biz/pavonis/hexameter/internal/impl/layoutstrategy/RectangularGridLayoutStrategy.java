package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static biz.pavonis.hexameter.api.CoordinateConverter.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.Map;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.internal.impl.HexagonImpl;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
public final class RectangularGridLayoutStrategy extends AbstractGridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		Map<String, Hexagon> hexagons = new HashMap<> ();
		for (int y = 0; y < builder.getGridHeight(); y++) {
			for (int x = 0; x < builder.getGridWidth(); x++) {
				int gridX = convertOffsetCoordinatesToAxialX(x, y, builder.getOrientation());
				int gridZ = convertOffsetCoordinatesToAxialZ(x, y, builder.getOrientation());
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), gridX, gridZ);
				hexagons.put(createKeyFromCoordinate(gridX, gridZ), hexagon);
			}
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return super.checkParameters(gridHeight, gridWidth);
	}
}
