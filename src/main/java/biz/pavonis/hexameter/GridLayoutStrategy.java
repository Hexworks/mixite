package biz.pavonis.hexameter;

import java.util.List;


interface GridLayoutStrategy {

	/**
	 * Returns an immutable list of {@link Hexagon}s created using the {@link HexagonalGridBuilder} supplied.
	 * 
	 * @param builder
	 * @return immutable list of {@link Hexagon}s.
	 */
	List<List<Hexagon>> createHexagons(HexagonalGridBuilder builder);

	/**
	 * Calculates the current offset from the left. This is necessary because of the
	 * diagonal nature of the y axis.
	 * 
	 * @param y current y coordinate
	 * @return current offset
	 */
	int calculateCurrentOffset(int y);

}
