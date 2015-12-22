package org.codetome.hexameter.internal.impl.layoutstrategy;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.round;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

import java.util.Collection;
import java.util.HashSet;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
public final class HexagonalGridLayoutStrategy implements GridLayoutStrategy {

	@Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final double gridSize = builder.getGridHeight();
		final Collection<Hexagon> hexagons = new HashSet<> ();
		int startX = FLAT_TOP.equals(builder.getOrientation()) ? (int) floor(gridSize / 2d) : (int) round(gridSize / 4d);
		final int hexRadius = (int) floor(gridSize / 2d);
		final int minX = startX - hexRadius;
		for (int y = 0; y < gridSize; y++) {
			final int distanceFromMid = Math.abs(hexRadius - y);
			for (int x = max(startX, minX); x <= max(startX, minX) + hexRadius + hexRadius - distanceFromMid; x++) {
				final int gridX = x;
				final int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - (int) floor(gridSize / 4d) : y;
				final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.add(newHexagon(builder.getSharedHexagonData(), coordinate));
			}
			startX--;
		}
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		final boolean superResult = GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
		final boolean result = gridHeight == gridWidth && abs(gridHeight % 2) == 1;
		return result && superResult;
	}

}
