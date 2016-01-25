package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.round;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
public final class HexagonalGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Set<AxialCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder) {
        Set<AxialCoordinate> coordinates = new LinkedHashSet<>();
        final double gridSize = builder.getGridHeight();
        int startX = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? (int) floor(gridSize / 2d) : (int) round(gridSize / 4d);
        final int hexRadius = (int) floor(gridSize / 2d);
        final int minX = startX - hexRadius;
        for (int y = 0; y < gridSize; y++) {
            final int distanceFromMid = abs(hexRadius - y);
            for (int x = max(startX, minX); x <= max(startX, minX) + hexRadius + hexRadius - distanceFromMid; x++) {
                final int gridX = x;
                final int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - (int) floor(gridSize / 4d) : y;
                coordinates.add(AxialCoordinate.fromCoordinates(gridX, gridZ));
            }
            startX--;
        }
        return coordinates;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        final boolean superResult = GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth && abs(gridHeight % 2) == 1;
        return result && superResult;
    }

}
