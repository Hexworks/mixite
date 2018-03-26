package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
public final class RectangularGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Iterable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        final List<CubeCoordinate> coords = new ArrayList<>();
        for (int y = 0; y < builder.getGridHeight(); y++) {
            for (int x = 0; x < builder.getGridWidth(); x++) {
                final int gridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, builder.getOrientation());
                final int gridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, builder.getOrientation());
                coords.add(CubeCoordinate.fromCoordinates(gridX, gridZ));
            }
        }
        return coords;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return checkCommonCase(gridHeight, gridWidth);
    }
}
