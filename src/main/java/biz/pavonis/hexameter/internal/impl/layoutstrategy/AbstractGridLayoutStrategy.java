package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import static biz.pavonis.hexameter.api.CoordinateConverter.createKeyFromCoordinate;

import java.util.Map;

import biz.pavonis.hexameter.api.AxialCoordinate;
import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.internal.impl.HexagonImpl;

public abstract class AbstractGridLayoutStrategy implements GridLayoutStrategy {

	protected void addCustomHexagons(HexagonalGridBuilder builder, Map<String, Hexagon> hexagons) {
		for (AxialCoordinate coord : builder.getCustomCoordinates()) {
			if (!hexagons.containsKey(createKeyFromCoordinate(coord.getGridX(), coord.getGridZ()))) {
				Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), coord.getGridX(), coord.getGridZ());
				hexagons.put(createKeyFromCoordinate(coord.getGridX(), coord.getGridZ()), hexagon);
			}
		}
	}
}
