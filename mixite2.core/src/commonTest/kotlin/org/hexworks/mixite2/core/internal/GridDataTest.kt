package org.hexworks.mixite2.core.internal

import org.hexworks.mixite2.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite2.core.api.HexagonOrientation.POINTY_TOP
import org.hexworks.mixite2.core.api.HexagonalGridLayout.RECTANGULAR
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class GridDataTest {

    lateinit var target: GridData

    @Test
    fun shouldProperlyReturnRadiusWhenGetRadiusIsCalled() {
        target = GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT)
        assertEquals(RADIUS, target.radius)
    }

    @Test
    fun shouldProperlyCalculateWidthWithPointyHexagonsWhenGetWidthIsCalled() {
        target = createWithPointy()
        val expectedWidth = sqrt(3.0) * RADIUS
        val actualWidth = target.hexagonWidth
        assertEquals(expectedWidth, actualWidth)
    }

    private fun createWithPointy(): GridData {
        return GridData(POINTY_TOP, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT)
    }

    @Test
    fun shouldProperlyCalculateWidthWithFlatHexagonsWhenGetWidthIsCalled() {
        target = createWithFlat()
        val expectedWidth = RADIUS * 3 / 2
        val actualWidth = target.hexagonWidth
        assertEquals(expectedWidth, actualWidth)
    }

    private fun createWithFlat(): GridData {
        return GridData(FLAT_TOP, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT)
    }

    @Test
    fun shouldProperlyCalculateHeightWithPointyHexagonsWhenGetHeightIsCalled() {
        target = createWithPointy()
        val expectedHeight = RADIUS * 3 / 2
        val actualHeight = target.hexagonHeight
        assertEquals(expectedHeight, actualHeight)
    }

    @Test
    fun shouldProperlyCalculateHeightWithFlatHexagonsWhenGetHeightIsCalled() {
        target = createWithFlat()
        val expectedHeight = sqrt(3.0) * RADIUS
        val actualHeight = target.hexagonHeight
        assertEquals(expectedHeight, actualHeight)
    }

    @Test
    fun shouldReturnProperOrientationWhenGetOrientationIsCalled() {
        target = GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT)
        assertEquals(ORIENTATION, target.orientation)
    }

    @Test
    fun shouldReturnProperCoordinateOffsetWhengetCoordinateOffsetIsCalled() {
        target = GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT)
        assertEquals(ORIENTATION.coordinateOffset, target.orientation.coordinateOffset)
    }

    companion object {

        private const val RADIUS = 30.0
        private const val GRID_WIDTH = 30
        private const val GRID_HEIGHT = 30
        private val ORIENTATION = FLAT_TOP
        private val GRID_LAYOUT = RECTANGULAR.gridLayoutStrategy
    }
}
