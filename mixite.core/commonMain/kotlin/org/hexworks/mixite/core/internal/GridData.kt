package org.hexworks.mixite.core.internal

import org.hexworks.mixite.core.api.HexagonOrientation
import org.hexworks.mixite.core.api.HexagonalGridLayout
import kotlin.math.sqrt

/**
 * Immutable class which holds the shared data between the [org.hexworks.mixite.core.api.Hexagon]s of a
 * [org.hexworks.mixite.core.api.HexagonalGrid] and the HexagonalGrid's own immutable properties.
 */
class GridData(val orientation: HexagonOrientation,
                    val gridLayout: HexagonalGridLayout,
                    val radius: Double,
                    val gridWidth: Int,
                    val gridHeight: Int) {

    val hexagonHeight: Double
    val hexagonWidth: Double

    init {
        this.hexagonHeight = if (HexagonOrientation.FLAT_TOP == orientation)
            calculateHeight(radius)
        else
            calculateWidth(radius)
        this.hexagonWidth = if (HexagonOrientation.FLAT_TOP == orientation)
            calculateWidth(radius)
        else
            calculateHeight(radius)
    }

    private fun calculateHeight(radius: Double): Double {
        return sqrt(3.0) * radius
    }

    private fun calculateWidth(radius: Double): Double {
        return radius * 3 / 2
    }
}
