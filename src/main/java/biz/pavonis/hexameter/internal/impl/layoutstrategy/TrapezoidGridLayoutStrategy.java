package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import static biz.pavonis.hexameter.api.CoordinateConverter.createKeyFromCoordinate;

import java.util.HashMap;
import java.util.Map;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.internal.impl.HexagonImpl;

public final class TrapezoidGridLayoutStrategy extends AbstractGridLayoutStrategy {

	public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
		Map<String, Hexagon> hexagons = new HashMap<String, Hexagon>();
		for (int y = 0; y < builder.getGridHeight(); y++) {
			for (int x = 0; x < builder.getGridWidth(); x++) {
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), x, y);
				hexagons.put(createKeyFromCoordinate(x, y), hexagon);
			}
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	public boolean checkParameters(int gridHeight, int gridWidth) {
		return true;
	}

}
