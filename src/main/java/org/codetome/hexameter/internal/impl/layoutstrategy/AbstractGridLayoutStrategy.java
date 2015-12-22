package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.Map;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;

public abstract class AbstractGridLayoutStrategy implements GridLayoutStrategy {

	protected void addCustomHexagons(final HexagonalGridBuilder builder, final Map<String, Hexagon> hexagons) {
		for (final AxialCoordinate coord : builder.getCustomCoordinates()) {
			if (hexagons.containsKey(coord.toKey())) {
				throw new IllegalArgumentException("There is already a hexagon in the grid with axial coordinates: (" + coord.getGridX() + "," + coord.getGridZ() + ")!");
			}
			hexagons.put(coord.toKey(), newHexagon(builder.getSharedHexagonData(), coord));
		}
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		return gridHeight > 0 && gridWidth > 0;
	}
}
