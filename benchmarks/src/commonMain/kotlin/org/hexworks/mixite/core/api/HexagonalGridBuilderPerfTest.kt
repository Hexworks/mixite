package org.hexworks.mixite.core.api

import kotlinx.benchmark.*
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
open class HexagonalGridBuilderPerfTest {

    @Param("RECTANGULAR", "HEXAGONAL", "TRIANGULAR", "TRAPEZOID")
    lateinit var layout: HexagonalGridLayout

    lateinit var builder: HexagonalGridBuilder<DefaultSatelliteData>

    @Setup
    fun setUp() {
        builder = HexagonalGridBuilder<DefaultSatelliteData>()
                .setRadius(100.0)
                .apply { gridLayoutWithConsistentSize(layout, this) }
    }

    private fun gridLayoutWithConsistentSize(layout: HexagonalGridLayout, builder: HexagonalGridBuilder<*>) {
        builder.setGridLayout(layout).apply {
            when (layout) {
                // Each of these is about 10000 in size. Resulting ops/sec should be consistent.
                HexagonalGridLayout.RECTANGULAR -> setGridHeight(100).setGridWidth(100)
                HexagonalGridLayout.TRAPEZOID -> setGridHeight(100).setGridWidth(100)
                HexagonalGridLayout.TRIANGULAR -> setGridHeight(141).setGridWidth(141)
                HexagonalGridLayout.HEXAGONAL -> setGridHeight(115).setGridWidth(115)
            }
        }
    }

    @Suppress("Unused")
    @Benchmark
    fun buildTime() = builder.build()

}
