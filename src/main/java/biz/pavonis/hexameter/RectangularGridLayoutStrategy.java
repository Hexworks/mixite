package biz.pavonis.hexameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import biz.pavonis.hexameter.HexagonImpl.Builder;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
class RectangularGridLayoutStrategy implements GridLayoutStrategy {

	private int gridHeight;

	@Override
	public List<List<Hexagon>> createHexagons(HexagonalGridBuilder builder) {
		gridHeight = builder.getGridHeight();
		ArrayList<List<Hexagon>> hexagons = new ArrayList<List<Hexagon>>(gridHeight);
		for (int y = 0; y < builder.getGridHeight(); y++) {
			List<Hexagon> innerList = new ArrayList<Hexagon>(builder.getGridWidth());
			for (int x = 0; x < builder.getGridWidth(); x++) {
				Builder hexBuilder = new HexagonImpl.Builder().orientation(builder.getOrientation())
						.radius(builder.getRadius()).gridX(x + calculateCurrentOffset(y)).gridY(y)
						.maxLeftOffset(calculateMaxLeftOffset());
				innerList.add(hexBuilder.build());
			}
			hexagons.add(Collections.unmodifiableList(innerList));
		}
		return Collections.unmodifiableList(hexagons);
	}

	@Override
	public int calculateCurrentOffset(int y) {
		return calculateMaxLeftOffset() - (int) (Math.floor(y / 2));
	}

	/**
	 * Calculates the maximum offset from the left (because of the diagonal axis).
	 * 
	 * @return offset
	 */
	private int calculateMaxLeftOffset() {
		return gridHeight / 2;
	}

}
