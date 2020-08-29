package org.hexworks.mixite.core.internal.impl

import org.hexworks.mixite.core.api.CoordinateConverter
import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.Hexagon
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CustomGridLayoutHexagonImplTest : HexagonalGridImplTest() {

    override fun getBuilder(): HexagonalGridBuilder<DefaultSatelliteData> {
        return HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridLayout(CustomGridLayoutStrategy())
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setRadius(RADIUS.toDouble())
                .setOrientation(ORIENTATION)
    }

    override fun shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid() {
        val hexagons = target.hexagons
        val expectedCoordinates = HashSet<String>()
        for (x in 0 until GRID_WIDTH) {
            for (y in 0 until GRID_HEIGHT) {
                if (y == 0 && x == 0 || y == 0 && x == GRID_WIDTH - 1 || y == GRID_HEIGHT - 1 && x == 0 ||
                        y == GRID_HEIGHT - 1 && x == GRID_WIDTH - 1) {
                    continue
                }
                val gridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, ORIENTATION)
                val gridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, ORIENTATION)
                expectedCoordinates.add("$gridX,$gridZ")
            }
        }
        var count = 0
        for (hexagon in hexagons) {
            expectedCoordinates.remove("${hexagon.gridX},${hexagon.gridZ}")
            count++
        }
        assertEquals(96, count)
        assertTrue(expectedCoordinates.isEmpty())
    }

    override fun shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge() {
        val hex = target.getByCubeCoordinate(CubeCoordinate.fromCoordinates(-4, 8)).get()
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(target.getByCubeCoordinate(CubeCoordinate.fromCoordinates(-3, 7)).get())
        expected.add(target.getByCubeCoordinate(CubeCoordinate.fromCoordinates(-3, 8)).get())
        val actual = target.getNeighborsOf(hex)
        assertEquals(expected, actual)
    }

    override fun shouldProperlyReturnGridLayoutWhenGetGridLayoutIsCalled() {
        assertTrue(target.gridData.gridLayout is CustomGridLayoutStrategy)
    }
}