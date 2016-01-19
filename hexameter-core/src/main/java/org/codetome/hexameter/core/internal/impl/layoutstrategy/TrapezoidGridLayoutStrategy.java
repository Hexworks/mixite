package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

public final class TrapezoidGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Set<AxialCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder) {
        Set<AxialCoordinate> coordinates = new LinkedHashSet<>();
        for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
            for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
                coordinates.add(fromCoordinates(gridX, gridZ));
            }
        }
        return coordinates;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
    }

}
