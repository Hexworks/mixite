package org.hexworks.mixite2.core.internal.impl.layoutstrategy

import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData

class TriangularGridLayoutStrategy : GridLayoutStrategy() {

    override fun fetchGridCoordinates(builder: HexagonalGridBuilder<out SatelliteData>): Iterable<org.hexworks.mixite2.core.api.CubeCoordinate> {
        val gridSize = builder.getGridHeight()
        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>(gridSize * (gridSize + 1) / 2)
        for (gridZ in 0 until gridSize) {
            val endX = gridSize - gridZ
            for (gridX in 0 until endX) {
                coords.add(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(gridX, gridZ))
            }
        }
        return coords
    }

    override fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean {
        val superResult = checkCommonCase(gridHeight, gridWidth)
        val result = gridHeight == gridWidth
        return superResult && result
    }

    override fun getName(): String {
        return "TRIANGULAR"
    }
}
