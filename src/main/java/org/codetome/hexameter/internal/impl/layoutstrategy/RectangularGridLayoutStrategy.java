package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;

import java.util.HashMap;
import java.util.Map;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.internal.impl.HexagonImpl;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
public final class RectangularGridLayoutStrategy extends AbstractGridLayoutStrategy {

	@Override
    public Map<String, Hexagon> createHexagons(final HexagonalGridBuilder builder) {
		final Map<String, Hexagon> hexagons = new HashMap<> ();
		for (int y = 0; y < builder.getGridHeight(); y++) {
			for (int x = 0; x < builder.getGridWidth(); x++) {
				final int gridX = convertOffsetCoordinatesToAxialX(x, y, builder.getOrientation());
				final int gridZ = convertOffsetCoordinatesToAxialZ(x, y, builder.getOrientation());
				final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.put(coordinate.toKey(), new HexagonImpl(builder.getSharedHexagonData(), coordinate));
			}
		}
		addCustomHexagons(builder, hexagons);
		return hexagons;
	}

	@Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
		return super.checkParameters(gridHeight, gridWidth);
	}
}
