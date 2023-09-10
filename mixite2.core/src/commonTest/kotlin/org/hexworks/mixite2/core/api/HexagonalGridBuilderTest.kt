package org.hexworks.mixite2.core.api

import org.hexworks.mixite2.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite2.core.api.HexagonalGridLayout.RECTANGULAR
import org.hexworks.mixite2.core.api.HexagonalGridLayout.TRIANGULAR
import org.hexworks.mixite2.core.api.contract.SatelliteData
import kotlin.test.*

class HexagonalGridBuilderTest {

    lateinit var target: HexagonalGridBuilder<out SatelliteData>

    @BeforeTest
    fun setUp() {
        target = HexagonalGridBuilder()
        target.setGridHeight(GRID_HEIGHT)
                .setGridLayout(GRID_LAYOUT)
                .setGridWidth(GRID_WIDTH)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS)
    }

    @Test
    fun shouldContainProperValuesWhenGettersAreCalled() {
        assertEquals(GRID_HEIGHT, target.getGridHeight())
        assertEquals(GRID_WIDTH, target.getGridWidth())
        assertEquals(GRID_LAYOUT_STRATEGY, target.gridLayoutStrategy)
        assertEquals(RADIUS, target.getRadius())
        assertNotNull(target.gridData)
    }

    @Test
    fun shouldFailGettingSharedHexagonDataWhenOrientationIsNull() {
        assertFailsWith<IllegalStateException> {
            target.setRadius(0.0)
            target.gridData
        }
    }


    @Test
    fun shouldFailBuildWhenSizeIsNotCompatibleWithLayout() {
        assertFailsWith<IllegalStateException> {
            target.setGridLayout(TRIANGULAR)
            target.setGridHeight(4)
            target.build()
        }
    }

    @Test
    fun shouldReturnProperOrientationWhenGetOrientationIsCalled() {
        assertEquals(ORIENTATION, target.getOrientation())
    }

    @Test
    fun shouldBuildWhenProperParametersArePresent() {
        val grid = target.build()
        assertNotNull(grid)
    }

    companion object {

        private const val GRID_HEIGHT = 9
        private const val GRID_WIDTH = 9
        private const val RADIUS = 30.0
        private val GRID_LAYOUT = RECTANGULAR
        private val GRID_LAYOUT_STRATEGY = RECTANGULAR.gridLayoutStrategy
        private val ORIENTATION = FLAT_TOP
    }

}
