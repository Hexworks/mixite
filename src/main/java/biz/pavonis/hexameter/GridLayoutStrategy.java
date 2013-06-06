package biz.pavonis.hexameter;

import java.util.Map;

interface GridLayoutStrategy {

	// TODO: javadoc
	Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder);

	/**
	 * Checks whether the supplied parameters are valid for the given strategy.
	 * <i>For example a hexagonal grid layout only works if the width equals to the height</i>
	 * 
	 * @param gridHeight
	 * @param gridWidth
	 * @return valid?
	 */
	boolean checkParameters(int gridHeight, int gridWidth);

}
