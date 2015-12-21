package org.codetome.hexameter.internal.impl.layoutstrategy;

import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;

/**
 * Represents the method of creating a {@link HexagonalGrid} corresponding to a given shape.
 */
public interface GridLayoutStrategy {

	/**
	 * Creates the {@link Hexagon} objects which fit in the shape
	 * of the requested layout.
	 * 
	 * @param builder contains the data needed
	 * @return created hexagons.
	 */
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
