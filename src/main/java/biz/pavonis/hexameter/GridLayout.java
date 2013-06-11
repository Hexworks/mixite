package biz.pavonis.hexameter;

/**
 * This enum represents the possible shapes a {@link HexagonalGrid} can have.
 */
public enum GridLayout {
	RECTANGULAR(new RectangularGridLayoutStrategy()), HEXAGONAL(null), TRIANGULAR(null);
	private GridLayoutStrategy gridLayoutStrategy;

	private GridLayout(GridLayoutStrategy gridLayoutStrategy) {
		this.gridLayoutStrategy = gridLayoutStrategy;
	}

	GridLayoutStrategy getGridLayoutStrategy() {
		return gridLayoutStrategy;
	}
}
