package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.Collection;
import java.util.LinkedList;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
public final class TriangularGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Collection<AxialCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder) {
        Collection<AxialCoordinate> coordinates = new LinkedList<>();
        final int gridSize = builder.getGridHeight();
        for (int gridZ = 0; gridZ < gridSize; gridZ++) {
            final int endX = gridSize - gridZ;
            for (int gridX = 0; gridX < endX; gridX++) {
                coordinates.add(fromCoordinates(gridX, gridZ));
            }
        }
        return coordinates;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        final boolean superResult = GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth;
        return superResult && result;
    }
}
