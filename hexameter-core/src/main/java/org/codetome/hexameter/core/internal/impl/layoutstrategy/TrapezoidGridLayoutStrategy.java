package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.Collection;
import java.util.LinkedList;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

public final class TrapezoidGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Collection<AxialCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder) {
        Collection<AxialCoordinate> coordinates = new LinkedList<>();
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
