package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.ArrayList;
import java.util.List;

public final class TrapezoidGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Iterable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        final List<CubeCoordinate> coords = new ArrayList<>();
        for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
            for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
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
