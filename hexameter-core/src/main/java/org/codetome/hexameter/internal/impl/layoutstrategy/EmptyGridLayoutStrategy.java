package org.codetome.hexameter.internal.impl.layoutstrategy;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

import java.util.Collection;
import java.util.HashSet;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
public final class EmptyGridLayoutStrategy implements GridLayoutStrategy {

    @Override
    public Collection<Hexagon> createHexagons(final HexagonalGridBuilder builder) {
        return new HashSet<>();
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return true;
    }
}
