package org.hexworks.mixite.core.internal

import org.hexworks.mixite.core.api.HexagonOrientation
import org.hexworks.mixite.core.api.HexagonalGridLayout
import org.hexworks.mixite.core.internal.impl.layoutstrategy.GridLayoutStrategy
import kotlin.math.sqrt

/**
 * Immutable class which holds the shared data between the [org.hexworks.mixite.core.api.Hexagon]s of a
 * [org.hexworks.mixite.core.api.HexagonalGrid] and the HexagonalGrid's own immutable properties.
 */
class GridData(val orientation: HexagonOrientation,
                    val gridLayout: GridLayoutStrategy,
                    val radius: Double,
                    val gridWidth: Int,
                    val gridHeight: Int) {

    val hexagonHeight: Double
    val hexagonWidth: Double
    val innerRadius: Double

    init {
        if (orientation === HexagonOrientation.FLAT_TOP) {
            // FIXME These are the wrong way around! flat-top => width > height
            hexagonHeight = calculateHeight(radius)
            hexagonWidth = calculateWidth(radius)
            innerRadius = hexagonWidth / 2
        } else {
            hexagonHeight = calculateWidth(radius)
            hexagonWidth = calculateHeight(radius)
            innerRadius = hexagonHeight / 2
        }
    }

    private fun calculateHeight(radius: Double) = sqrt(3.0) * radius

    private fun calculateWidth(radius: Double) = radius * 3 / 2
}
