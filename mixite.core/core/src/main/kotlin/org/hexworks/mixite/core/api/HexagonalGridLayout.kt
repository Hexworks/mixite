package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.internal.impl.layoutstrategy.*

/**
 * This enum represents the possible shapes a [HexagonalGrid] can have.
 * The name [HexagonalGridLayout] might seem inconsistent with the other names
 * in this package but since the name GridLayout is so common (in SWT for example)
 * using this name seemed appropriate.
 */
enum class HexagonalGridLayout(val gridLayoutStrategy: GridLayoutStrategy) {

    /**
     * A rectangular layout has no special rules.
     */
    RECTANGULAR(RectangularGridLayoutStrategy()),

    /**
     * The hexagonal layout must have equal width and height and
     * it must be odd.
     */
    HEXAGONAL(HexagonalGridLayoutStrategy()),

    /**
     * A triangular layout must have equal width and height.
     */
    TRIANGULAR(TriangularGridLayoutStrategy()),

    /**
     * A trapezoid layout has no special rules.
     */
    TRAPEZOID(TrapezoidGridLayoutStrategy());

    /**
     * Checks whether the grid height/width parameters can be used for the given [GridLayoutStrategy].
     *
     * @return valid?
     */
    internal fun checkParameters(gridHeight: Int, gridWidth: Int): Boolean {
        return gridLayoutStrategy.checkParameters(gridHeight, gridWidth)
    }
}
