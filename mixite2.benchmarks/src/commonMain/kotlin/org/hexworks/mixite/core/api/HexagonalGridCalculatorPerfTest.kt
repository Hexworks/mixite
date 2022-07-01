package org.hexworks.mixite.core.api

import kotlinx.benchmark.*
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import org.hexworks.mixite.core.internal.impl.HexagonalGridCalculatorImpl

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Suppress("Unused")
open class HexagonalGridCalculatorPerfTest {

    private lateinit var calc: HexagonalGridCalculatorImpl<DefaultSatelliteData>
    private lateinit var hexFrom: Hexagon<DefaultSatelliteData>
    private lateinit var hexTo: Hexagon<DefaultSatelliteData>
    private lateinit var grid: HexagonalGrid<DefaultSatelliteData>

    @Setup
    fun setUp() {
        grid = HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                .setGridHeight(100)
                .setGridWidth(100)
                .setRadius(10.0)
                .build()
        calc = HexagonalGridCalculatorImpl(grid)
        hexFrom = grid.getByCubeCoordinate(CubeCoordinate.fromCoordinates(2, 2)).get()
        hexTo = grid.getByCubeCoordinate(CubeCoordinate.fromCoordinates(7, 9)).get()
    }

    @Benchmark
    fun calculateRingFrom(bh: Blackhole) {
        val hexs = calc.calculateRingFrom(hexFrom, 2)
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

    @Benchmark
    fun calculateMovementRangeFrom(bh: Blackhole) {
        val hexs = calc.calculateMovementRangeFrom(hexFrom, 2)
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

    @Benchmark
    fun drawLine(bh: Blackhole) {
        val hexs = calc.drawLine(hexFrom, hexTo)
        for (hexagon in hexs) {
            bh.consume(hexagon)
        }
    }

}
