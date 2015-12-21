package org.codetome.hexameter.internal.impl.layoutstrategy;

import java.util.Map;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.internal.impl.HexagonImpl;

public abstract class AbstractGridLayoutStrategy implements GridLayoutStrategy {

	protected void addCustomHexagons(final HexagonalGridBuilder builder, final Map<String, Hexagon> hexagons) {
		for (final AxialCoordinate coord : builder.getCustomCoordinates()) {
			if (hexagons.containsKey(coord.toKey())) {
				throw new IllegalArgumentException("There is already a hexagon in the grid with axial coordinates: (" + coord.getGridX() + "," + coord.getGridZ() + ")!");
			}
			hexagons.put(coord.toKey(), new HexagonImpl(builder.getSharedHexagonData(), coord));
		}
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		return gridHeight > 0 && gridWidth > 0;
	}
}
