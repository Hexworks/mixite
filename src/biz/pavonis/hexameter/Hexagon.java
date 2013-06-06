package biz.pavonis.hexameter;

/**
 * <p>
 * Represents a Hexagon.
 * </p>
 * <em>Please note</em> that all coordinates are relative to the
 * {@link HexagonalGrid} containing this {@link Hexagon}.
 */
public interface Hexagon {

	/**
	 * Returns an array containing the {@link Point}s of this {@link Hexagon}.
	 * 
	 * @return points array
	 */
	Point[] getPoints();

	/**
	 * Returns this {@link Hexagon}'s <b>x</b> coordinate on the
	 * {@link HexagonalGrid}.
	 * 
	 * @return x coordinate on the grid
	 */
	int getGridX();

	/**
	 * Returns this {@link Hexagon}'s <b>y</b> coordinate on the
	 * {@link HexagonalGrid}.
	 * 
	 * @return y coordinate on the grid
	 */
	int getGridY();

	/**
	 * Returns the center <b>x</b> coordinate of this {@link Hexagon}.
	 * 
	 * @return center x
	 */
	double getCenterX();

	/**
	 * Returns the center <b>y</b> coordinate of this {@link Hexagon}.
	 * 
	 * @return center y
	 */
	double getCenterY();

}
