package org.hexworks.mixite.core.api

import kotlinx.benchmark.*
import org.hexworks.mixite.core.api.contract.HexagonDataStorage
import org.hexworks.mixite.core.api.defaults.DefaultHexagonDataStorage
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import org.hexworks.mixite.core.internal.GridData

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Suppress("Unused")
open class HexagonPerfTest {

    private lateinit var gridData: GridData
    private lateinit var cc: CubeCoordinate
    private lateinit var store: HexagonDataStorage<DefaultSatelliteData>

    @Setup
    fun setUp() {
        gridData = HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                .setGridHeight(1)
                .setGridWidth(1)
                .setRadius(10.0)
                .build()
                .gridData
        cc = CubeCoordinate.fromCoordinates(-49, 0)
        store = DefaultHexagonDataStorage()
    }


    //    @Benchmark
    fun constructHexagon(bh: Blackhole) {
//        bh.consume(HexagonImpl(gridData, cc, store))
    }
}
