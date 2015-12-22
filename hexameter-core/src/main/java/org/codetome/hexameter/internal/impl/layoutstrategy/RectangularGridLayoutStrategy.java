package org.codetome.hexameter.internal.impl.layoutstrategy;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

import java.util.Collection;
import java.util.HashSet;

import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static org.codetome.hexameter.internal.impl.HexagonImpl.newHexagon;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
public final class RectangularGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
        final Collection<Hexagon> hexagons = new HashSet<>();
        for (int y = 0; y < builder.getGridHeight(); y++) {
            for (int x = 0; x < builder.getGridWidth(); x++) {
                final int gridX = convertOffsetCoordinatesToAxialX(x, y, builder.getOrientation());
                final int gridZ = convertOffsetCoordinatesToAxialZ(x, y, builder.getOrientation());
                final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
                hexagons.add(newHexagon(builder.getSharedHexagonData(), coordinate));
            }
        }
        return hexagons;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return GridLayoutStrategy.super.checkParameters(gridHeight, gridWidth);
    }
}
