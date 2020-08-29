package org.hexworks.mixite.core.internal.impl

import org.hexworks.mixite.core.api.CoordinateConverter
import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.internal.impl.layoutstrategy.GridLayoutStrategy

class CustomGridLayoutStrategy : GridLayoutStrategy() {

    override fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<CubeCoordinate> {
        val coords = ArrayList<CubeCoordinate>( builder.getGridHeight() * builder.getGridWidth())
        for (y in 0 until builder.getGridHeight()) {
            val xStart = if (y == 0 || y == builder.getGridHeight() - 1) 1 else 0
            val xEnd = if (y == 0 || y == builder.getGridHeight() - 1) builder.getGridWidth() - 1
            else builder.getGridWidth()
            for (x in xStart until xEnd) {
                val gridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, builder.getOrientation())
                val gridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, builder.getOrientation())
                coords.add(CubeCoordinate.fromCoordinates(gridX, gridZ))
            }
        }
        return coords
    }

    override fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean {
        return true
    }

    override fun getName(): String {
        return "CUSTOM"
    }
}
