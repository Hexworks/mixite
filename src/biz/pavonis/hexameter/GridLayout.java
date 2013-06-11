package biz.pavonis.hexameter;

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
