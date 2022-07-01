package org.hexworks.mixite2.core

import org.hexworks.mixite2.core.api.HexagonOrientation
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData

object GridLayoutStrategyTestUtil {

    private const val RADIUS = 30.0
    private const val GRID_WIDTH = 3
    private const val GRID_HEIGHT = 3
    private val ORIENTATION = HexagonOrientation.POINTY_TOP

    fun fetchDefaultBuilder(): HexagonalGridBuilder<out SatelliteData> {
        return HexagonalGridBuilder<SatelliteData>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setRadius(RADIUS)
                .setOrientation(ORIENTATION)
    }
}
