package biz.pavonis.hexameter;

/**
 * This enum represents the possible shapes a {@link HexagonalGrid} can have.
 * The name {@link HexagonGridLayout} might seem inconsistent with the other names
 * in this package but since the name GridLayout is so common (in SWT for example)
 * using this name seemed appropriate.
 */
public enum HexagonGridLayout {
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
	TRIANGULAR(new TriangularGridLayoutStrategy());

	private GridLayoutStrategy gridLayoutStrategy;

	private HexagonGridLayout(GridLayoutStrategy gridLayoutStrategy) {
		this.gridLayoutStrategy = gridLayoutStrategy;
	}

	GridLayoutStrategy getGridLayoutStrategy() {
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
