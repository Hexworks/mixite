package org.hexworks.mixite.core.api

import kotlinx.benchmark.*
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Suppress("Unused")
open class HexagonalGridPerfTest {

    private lateinit var hexRandom: Hexagon<DefaultSatelliteData>
    private lateinit var ccTo: CubeCoordinate
    private lateinit var ccFrom: CubeCoordinate
    private lateinit var grid: HexagonalGrid<DefaultSatelliteData>
    private lateinit var spiralPath: List<Point>
    private lateinit var circlePath: List<Point>

    @Setup
    fun setUp() {
        grid = HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                .setGridHeight(100)
                .setGridWidth(100)
                .setRadius(10.0)
                .build()
        ccFrom = CubeCoordinate.fromCoordinates(-49, 0)
        ccTo = CubeCoordinate.fromCoordinates(99, 99)
        hexRandom = grid.getByCubeCoordinate(CubeCoordinate.fromCoordinates(1, 1)).get()
    }

    @Setup
    fun createSpiralMousePath() {
        val steps = 24 // 24 points around a circle
        val turns = 10
        val points = ArrayList<Point>()
        for (p in 0..steps * turns) {
            val angle = (steps * 2 * PI) / (p % steps)
            val distance = 2000.0 * p / (steps * turns)
            points += Point.fromPosition(
                    distance * cos(angle) + 1000,
                    distance * sin(angle) + 500)
        }
        spiralPath = points
    }

    @Setup
    fun createCircularMousePath() {
        val steps = 24 // 24 points around a circle
        val turns = 10
        val points = ArrayList<Point>()
        for (p in 0..steps * turns) {
            val angle = (steps * 2 * PI) / (p % steps)
            val distance = 300
            points += Point.fromPosition(
                    distance * cos(angle) + 1000,
                    distance * sin(angle) + 500)
        }
        circlePath = points
    }

    @Benchmark
    fun iterateHexagons(bh: Blackhole) {
        val hexs = grid.hexagons
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

    @Benchmark
    fun getHexagonsByOffsetRange(bh: Blackhole) {
        // Gets all hexs so the ops/sec is comparable to iterateHexagons
        val hexs = grid.getHexagonsByOffsetRange(0, 100, 0, 100)
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

    @Benchmark
    fun getHexagonsByCubeRange(bh: Blackhole) {
        // Gets all hexs so the ops/sec is comparable to iterateHexagons
        // Note % of CC are not in the grid
        val hexs = grid.getHexagonsByCubeRange(ccFrom, ccTo)
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

    @Benchmark
    fun getByPixelCoordinate50pct(bh: Blackhole) {
        // ~ 50% hit ratio
        for ((x, y) in spiralPath) {
            bh.consume(grid.getByPixelCoordinate(x, y))
        }
    }

    @Benchmark
    fun getByPixelCoordinate100pct(bh: Blackhole) {
        // ~ 100% hit ratio
        for ((x, y) in circlePath) {
            bh.consume(grid.getByPixelCoordinate(x, y))
        }
    }

    @Benchmark
    fun getNeighborsOf(bh: Blackhole) {
        bh.consume(grid.getNeighborsOf(hexRandom))
    }

}
