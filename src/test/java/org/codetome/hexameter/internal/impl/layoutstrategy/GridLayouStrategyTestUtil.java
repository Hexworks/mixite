package org.codetome.hexameter.internal.impl.layoutstrategy;

import static org.codetome.hexameter.api.HexagonOrientation.POINTY_TOP;

import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.HexagonalGridBuilder;

public class GridLayouStrategyTestUtil {

	private static final int RADIUS = 30;
	private static final int GRID_WIDTH = 3;
	private static final int GRID_HEIGHT = 3;
	private static final HexagonOrientation ORIENTATION = POINTY_TOP;

	public static HexagonalGridBuilder fetchDefaultBuilder() {
		return new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
	}
}
