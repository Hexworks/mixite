package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.Collection;
import java.util.HashSet;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;

public final class TrapezoidGridLayoutStrategy implements GridLayoutStrategy {

	@Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final Collection<Hexagon> hexagons = new HashSet<>();
		for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
			for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
			    final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.add(newHexagon(builder.getSharedHexagonData(), coordinate));
			}
		}
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		return GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
	}

}
