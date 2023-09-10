package org.hexworks.mixite2.core.internal.impl

import org.hexworks.mixite2.core.api.CoordinateConverter
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData
import org.hexworks.mixite2.core.internal.impl.layoutstrategy.GridLayoutStrategy

class CustomGridLayoutStrategy : GridLayoutStrategy() {

    override fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<org.hexworks.mixite2.core.api.CubeCoordinate> {
        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>( builder.getGridHeight() * builder.getGridWidth())
        for (y in 0 until builder.getGridHeight()) {
            val xStart = if (y == 0 || y == builder.getGridHeight() - 1) 1 else 0
            val xEnd = if (y == 0 || y == builder.getGridHeight() - 1) builder.getGridWidth() - 1
            else builder.getGridWidth()
            for (x in xStart until xEnd) {
                val gridX = org.hexworks.mixite2.core.api.CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, builder.getOrientation())
                val gridZ = org.hexworks.mixite2.core.api.CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, builder.getOrientation())
                coords.add(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(gridX, gridZ))
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
