package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.internal.impl.HexagonImpl;

import java.util.Collection;
import java.util.HashSet;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

public final class TrapezoidGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
        final Collection<Hexagon> hexagons = new HashSet<>();
        for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
            for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
                final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.add(HexagonImpl.newHexagon(builder.getSharedHexagonData(), coordinate));
            }
        }
        return hexagons;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
    }

}
