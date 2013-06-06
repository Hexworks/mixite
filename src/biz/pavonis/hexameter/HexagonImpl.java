package biz.pavonis.hexameter;

class HexagonImpl implements Hexagon {

	private final HexagonOrientation orientation;
	private final double radius;
	private final double centerX;
	private final double centerY;
	/**
	 * The height of a {@link Hexagon} is the distance between its two flat sides.
	 */
	private final double height;
	private final int leftOffset;
	private final double side;
	private int gridX;
	private int gridY;

	/**
	 * This {@link Builder} is a convenient way of creating {@link Hexagon}s
	 * without the hassle of multi-parameter constructors.
	 */
	static class Builder {
		private HexagonOrientation orientation;
		private double radius;
		private int gridX;
		private int gridY;
		private int leftOffset;

		public Builder orientation(HexagonOrientation orientation) {
			this.orientation = orientation;
			return this;
		}

		public Builder radius(double radius) {
			this.radius = radius;
			return this;
		}

		public Builder gridX(int gridX) {
			this.gridX = gridX;
			return this;
		}

		public Builder gridY(int gridY) {
			this.gridY = gridY;
			return this;
		}

		public Builder maxLeftOffset(int leftOffset) {
			this.leftOffset = leftOffset;
			return this;
		}

		public Hexagon build() {
			return new HexagonImpl(this);
		}

	}

	HexagonImpl(Builder builder) {
		this.orientation = builder.orientation;
		this.radius = builder.radius;
		this.gridX = builder.gridX;
		this.gridY = builder.gridY;
		this.leftOffset = builder.leftOffset;
		this.height = calculateHeight(radius);
		this.side = calculateSide(radius);
		if (HexagonOrientation.FLAT.equals(orientation)) {
			this.centerX = gridX * 3 * radius + gridY * side + radius;
			this.centerY = gridY * height / 2 + height / 2;
		} else {
			this.centerX = gridX * height + gridY * height / 2 + height / 2;
			this.centerY = gridY * side + radius;
		}
	}

	private double calculateSide(double radius) {
		return radius * 3 / 2;
	}

	private double calculateHeight(double radius) {
		return Math.sqrt(3) * radius;
	}

	/**
	 * Since the
	 * 
	 * @return
	 */
	private double calculateOffset() {
		double ret;
		if (HexagonOrientation.POINTY.equals(orientation)) {
			ret = leftOffset * height;
		} else {
			ret = leftOffset * radius * 3;
		}
		return ret;
	}

	@Override
	public Point[] getPoints() {
		Point[] points = new Point[6];
		for (int i = 0; i < 6; i++) {
			double angle = 2 * Math.PI / 6 * (i + orientation.getCoordinateOffset());
			points[i] = new Point(centerX + radius * Math.cos(angle) - calculateOffset(), centerY + radius
					* Math.sin(angle));
		}
		return points;
	}

	@Override
	public int getGridX() {
		return gridX;
	}

	@Override
	public int getGridY() {
		return gridY;
	}

	@Override
	public double getCenterX() {
		return centerX - calculateOffset();
	}

	@Override
	public double getCenterY() {
		return centerY;
	}

}
