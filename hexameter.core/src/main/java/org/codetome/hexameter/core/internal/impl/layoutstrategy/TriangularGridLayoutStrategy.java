package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
public final class TriangularGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Iterable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        final List<CubeCoordinate> coords = new ArrayList<>();
        final int gridSize = builder.getGridHeight();
        for (int gridZ = 0; gridZ < gridSize; gridZ++) {
            final int endX = gridSize - gridZ;
            for (int gridX = 0; gridX < endX; gridX++) {
                coords.add(CubeCoordinate.fromCoordinates(gridX, gridZ));
            }
        }
        return coords;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        final boolean superResult = checkCommonCase(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth;
        return superResult && result;
    }
}
