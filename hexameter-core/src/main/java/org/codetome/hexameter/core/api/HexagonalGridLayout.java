package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.internal.impl.layoutstrategy.*;

/**
 * This enum represents the possible shapes a {@link HexagonalGrid} can have.
 * The name {@link HexagonalGridLayout} might seem inconsistent with the other names
 * in this package but since the name GridLayout is so common (in SWT for example)
 * using this name seemed appropriate.
 */
public enum HexagonalGridLayout {

    /**
     * A rectangular layout has no special rules.
     */
    RECTANGULAR(new RectangularGridLayoutStrategy()),

    /**
     * The hexagonal layout must have equal width and height and
     * it must be odd.
     */
    HEXAGONAL(new HexagonalGridLayoutStrategy()),

    /**
     * A triangular layout must have equal width and height.
     */
    TRIANGULAR(new TriangularGridLayoutStrategy()),

    /**
     * A trapezoid layout has no special rules.
     */
    TRAPEZOID(new TrapezoidGridLayoutStrategy()),

    /**
     * An empty grid.
     */
    EMPTY(new EmptyGridLayoutStrategy());

    private GridLayoutStrategy gridLayoutStrategy;

    HexagonalGridLayout(final GridLayoutStrategy gridLayoutStrategy) {
        this.gridLayoutStrategy = gridLayoutStrategy;
    }

    /**
     * Checks whether the grid height/width parameters can be used for the given {@link GridLayoutStrategy}.
     *
     * @param gridHeight
     * @param gridWidth
     *
     * @return valid?
     */
    boolean checkParameters(final int gridHeight, final int gridWidth) {
        return getGridLayoutStrategy().checkParameters(gridHeight, gridWidth);
    }

    public GridLayoutStrategy getGridLayoutStrategy() {
        return gridLayoutStrategy;
    }
}
