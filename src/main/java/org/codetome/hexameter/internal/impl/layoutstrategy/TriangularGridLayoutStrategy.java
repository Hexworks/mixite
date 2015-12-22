package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.HashMap;
import java.util.Map;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
public final class TriangularGridLayoutStrategy extends AbstractGridLayoutStrategy {

	@Override
    public Map<String, Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final int gridSize = builder.getGridHeight();
		final Map<String, Hexagon> hexagons = new HashMap<> ();
		for (int gridZ = 0; gridZ < gridSize; gridZ++) {
			final int endX = gridSize - gridZ;
			for (int gridX = 0; gridX < endX; gridX++) {
			    final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
				hexagons.put(coordinate.toKey(), newHexagon(builder.getSharedHexagonData(), coordinate));
			}
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		final boolean superResult = super.checkParameters(gridHeight, gridWidth);
		final boolean result = gridHeight == gridWidth;
		return superResult && result;
	}
}
