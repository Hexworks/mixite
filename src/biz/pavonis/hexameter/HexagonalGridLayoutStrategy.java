package biz.pavonis.hexameter;

import java.util.List;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
class HexagonalGridLayoutStrategy implements GridLayoutStrategy {

	@Override
	public List<List<Hexagon>> createHexagons(HexagonalGridBuilder builder) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public int calculateCurrentOffset(int y) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

}
