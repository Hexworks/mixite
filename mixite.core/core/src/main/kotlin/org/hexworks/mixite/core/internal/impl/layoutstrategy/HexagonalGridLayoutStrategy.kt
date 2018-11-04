package org.hexworks.mixite.core.internal.impl.layoutstrategy

import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.HexagonOrientation
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.contract.SatelliteData
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.round

class HexagonalGridLayoutStrategy : GridLayoutStrategy() {

    override fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<CubeCoordinate> {
        val coords = ArrayList<CubeCoordinate>()
        val gridSize = builder.getGridHeight()
        var startX = if (HexagonOrientation.FLAT_TOP.equals(builder.getOrientation())) floor(gridSize / 2.0).toInt() else round(gridSize / 4.0).toInt()
        val hexRadius = floor(gridSize / 2.0).toInt()
        val minX = startX - hexRadius
        var y = 0
        while (y < gridSize) {
            val distanceFromMid = abs(hexRadius - y)
            for (x in max(startX, minX)..max(startX, minX) + hexRadius + hexRadius - distanceFromMid) {
                val gridZ = if (HexagonOrientation.FLAT_TOP.equals(builder.getOrientation())) y - floor(gridSize / 4.0).toInt() else y
                coords.add(CubeCoordinate.fromCoordinates(x, gridZ))
            }
            startX--
            y++
        }
        return coords
    }

    override fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean {
        val superResult = checkCommonCase(gridHeight, gridWidth)
        val result = gridHeight == gridWidth && abs(gridHeight % 2) == 1
        return result && superResult
    }

}
