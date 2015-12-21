package org.codetome.hexameter.internal.impl.layoutstrategy;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.round;
import static org.codetome.hexameter.api.CoordinateConverter.createKeyFromCoordinate;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;

import java.util.HashMap;
import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.internal.impl.HexagonImpl;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
public final class HexagonalGridLayoutStrategy extends AbstractGridLayoutStrategy {

	@Override
    public Map<String, Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final double gridSize = builder.getGridHeight();
		final Map<String, Hexagon> hexagons = new HashMap<> ();
		int startX = FLAT_TOP.equals(builder.getOrientation()) ? (int) floor(gridSize / 2d) : (int) round(gridSize / 4d);
		final int hexRadius = (int) floor(gridSize / 2d);
		final int minX = startX - hexRadius;
		for (int y = 0; y < gridSize; y++) {
			final int distanceFromMid = Math.abs(hexRadius - y);
			for (int x = max(startX, minX); x <= max(startX, minX) + hexRadius + hexRadius - distanceFromMid; x++) {
				final int gridX = x;
				final int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - (int) floor(gridSize / 4d) : y;
				final Hexagon hexagon = new HexagonImpl(builder.getSharedHexagonData(), gridX, gridZ);
				hexagons.put(createKeyFromCoordinate(gridX, gridZ), hexagon);
			}
			startX--;
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		final boolean superResult = super.checkParameters(gridHeight, gridWidth);
		final boolean result = gridHeight == gridWidth && abs(gridHeight % 2) == 1;
		return result && superResult;
	}

}
