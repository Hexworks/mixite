package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import java.util.HashMap;
import java.util.Map;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
public final class CustomGridLayoutStrategy extends AbstractGridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return super.checkParameters(gridHeight, gridWidth);
	}
}
