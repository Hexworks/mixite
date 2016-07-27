package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import rx.Observable;

/**
 * Represents the method of creating a {@link HexagonalGrid} corresponding to a given shape.
 */
public abstract class GridLayoutStrategy {

    /**
     * Fetches a monotonically increasing (from left to right, top to bottom) Set of
     * grid coordinates corresponding to the shape of the requested grid layout.
     *
     * @param builder builder
     *
     * @return observable
     */
    public abstract Observable<CubeCoordinate> fetchGridCoordinates(HexagonalGridBuilder builder);

    /**
     * Checks whether the supplied parameters are valid for the given strategy.
     * <i>For example a hexagonal grid layout only works if the width equals to the height</i>
     *
     * @param gridHeight height
     * @param gridWidth width
     *
     * @return valid?
     */
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return gridHeight > 0 && gridWidth > 0;
    }

}
