package org.hexworks.mixite.core

import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.junit.Test
import java.lang.String.format
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

class StressTest {

    lateinit var hexagonalGridBuilder: HexagonalGridBuilder<out SatelliteData>

    @BeforeTest
    fun setUp() {
        hexagonalGridBuilder = HexagonalGridBuilder<SatelliteData>()
                .setGridHeight(BIG_GRID_HEIGHT)
                .setGridWidth(BIG_GRID_WIDTH)
                .setRadius(BIG_GRID_RADIUS)
    }

    @Test
    fun shouldBeAbleToCreateBigGrid() {
        val start = System.nanoTime()
        hexagonalGridBuilder.build()
        val end = System.nanoTime()
        assertTrue {
            nanoToMs(end - start) < EXPECTED_MAXIMUM_GENERATION_TIME
        }
    }

    @Test
    fun shouldBeAbleToFetchHexesFromBigGrids() {
        val grid = hexagonalGridBuilder.build()

        val start = System.nanoTime()
        val ai = AtomicInteger()
        for (o in grid.hexagons) {
            ai.incrementAndGet()
        }
        val end = System.nanoTime()
        println(format("Number of hexes: %s, generated in: %dms.", ai.get(), nanoToMs(end - start)))
        assertTrue {
            nanoToMs(end - start) < EXPECTED_MAXIMUM_FETCH_TIME
        }
    }

    private fun nanoToMs(nanoTime: Long): Long {
        return nanoTime / 1000 / 1000
    }

    companion object {

        private const val BIG_GRID_HEIGHT = 1000
        private const val BIG_GRID_WIDTH = 1000
        private const val BIG_GRID_RADIUS = 50.0
        private const val EXPECTED_MAXIMUM_GENERATION_TIME: Long = 3000
        private const val EXPECTED_MAXIMUM_FETCH_TIME: Long = 3000
    }
}
