package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.Collection;
import java.util.HashSet;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
public final class TriangularGridLayoutStrategy implements GridLayoutStrategy {

	@Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final int gridSize = builder.getGridHeight();
		final Collection<Hexagon> hexagons = new HashSet<> ();
		for (int gridZ = 0; gridZ < gridSize; gridZ++) {
			final int endX = gridSize - gridZ;
			for (int gridX = 0; gridX < endX; gridX++) {
			    final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
				hexagons.add(newHexagon(builder.getSharedHexagonData(), coordinate));
			}
		}
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		final boolean superResult = GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
		final boolean result = gridHeight == gridWidth;
		return superResult && result;
	}
}
