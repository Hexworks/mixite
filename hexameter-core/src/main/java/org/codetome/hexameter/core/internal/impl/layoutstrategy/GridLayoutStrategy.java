package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import java.util.Set;

/**
 * Represents the method of creating a {@link HexagonalGrid} corresponding to a given shape.
 */
public interface GridLayoutStrategy {

    /**
     * Fetches a monotonically increasing (from left to right, top to bottom) Set of
     * grid coordinates corresponding to the shape of the requested grid layout.
     * @param builder
     * @return
     */
    Set<AxialCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder);

    /**
     * Checks whether the supplied parameters are valid for the given strategy.
     * <i>For example a hexagonal grid layout only works if the width equals to the height</i>
     *
     * @param gridHeight
     * @param gridWidth
     *
     * @return valid?
     */
    default boolean checkParameters(final int gridHeight, final int gridWidth) {
        return gridHeight > 0 && gridWidth > 0;
    }

}
