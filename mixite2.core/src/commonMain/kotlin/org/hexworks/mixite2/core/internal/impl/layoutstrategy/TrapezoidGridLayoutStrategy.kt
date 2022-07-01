package org.hexworks.mixite2.core.internal.impl.layoutstrategy

import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData

class TrapezoidGridLayoutStrategy : GridLayoutStrategy() {

    override fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<org.hexworks.mixite2.core.api.CubeCoordinate> {
        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>(builder.getGridHeight() * builder.getGridWidth())
        for (gridZ in 0 until builder.getGridHeight()) {
            for (gridX in 0 until builder.getGridWidth()) {
                coords.add(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(gridX, gridZ))
            }
        }
        return coords
    }

    override fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean {
        return checkCommonCase(gridHeight, gridWidth)
    }

    override fun getName(): String {
        return "TRAPEZOID"
    }
}
