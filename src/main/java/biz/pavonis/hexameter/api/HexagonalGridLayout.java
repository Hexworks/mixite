package biz.pavonis.hexameter.api;

import biz.pavonis.hexameter.internal.impl.layoutstrategy.CustomGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.RectangularGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.RhombusGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.TriangularGridLayoutStrategy;

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
	 * A rhombus layout has no special rules.
	 */
	RHOMBUS(new RhombusGridLayoutStrategy()),

	/**
	 * Represents a custom grid layout strategy. It will
	 * add {@link Hexagon}s to the grid based on the coordinates
	 * set by the user.
	 */
	CUSTOM(new CustomGridLayoutStrategy());

	private GridLayoutStrategy gridLayoutStrategy;

	private HexagonalGridLayout(GridLayoutStrategy gridLayoutStrategy) {
		this.gridLayoutStrategy = gridLayoutStrategy;
	}

	public GridLayoutStrategy getGridLayoutStrategy() {
		return gridLayoutStrategy;
	}

	/**
	 * Checks whether the grid height/width parameters can be used for the given {@link GridLayoutStrategy}.
	 * 
	 * @param gridHeight
	 * @param gridWidth
	 * @return valid?
	 */
	boolean checkParameters(int gridHeight, int gridWidth) {
		return getGridLayoutStrategy().checkParameters(gridHeight, gridWidth);
	}
}
