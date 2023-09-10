package org.hexworks.mixite2.core.internal.impl.layoutstrategy

import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData

/**
 * Represents the method of creating a [org.hexworks.mixite.core.api.HexagonalGrid] corresponding to a given shape.
 */
abstract class GridLayoutStrategy {

    /**
     * Fetches a monotonically increasing (from left to right, top to bottom) Set of
     * grid coordinates corresponding to the shape of the requested grid layout.
     *
     * @param builder builder
     *
     * @return observable
     */
    abstract fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<org.hexworks.mixite2.core.api.CubeCoordinate>

    /**
     * Checks whether the supplied parameters are valid for the given strategy.
     * *For example a hexagonal grid layout only works if the width equals to the height*
     *
     * @param gridHeight height
     * @param gridWidth width
     *
     * @return valid?
     */
    abstract fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean

    protected fun checkCommonCase(gridHeight: Int, gridWidth: Int): Boolean {
        return gridHeight > 0 && gridWidth > 0
    }

    /**
     * Returns the name of this strategy.
     *
     * @return name
     */
    abstract fun getName() : String

}
