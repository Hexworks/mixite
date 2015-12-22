package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.HashMap;
import java.util.Map;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;

public final class TrapezoidGridLayoutStrategy extends AbstractGridLayoutStrategy {

	@Override
    public Map<String, Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final Map<String, Hexagon> hexagons = new HashMap<>();
		for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
			for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
			    final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.put(coordinate.toKey(), newHexagon(builder.getSharedHexagonData(), coordinate));
			}
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		return true;
	}

}
