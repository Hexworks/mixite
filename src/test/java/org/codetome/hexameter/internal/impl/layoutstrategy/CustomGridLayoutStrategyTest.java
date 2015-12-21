package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertTrue;

import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.internal.impl.layoutstrategy.CustomGridLayoutStrategy;
import org.junit.Before;
import org.junit.Test;

public class CustomGridLayoutStrategyTest {

	private static final int RADIUS = 30;
	private static final int GRID_WIDTH = 0;
	private static final int GRID_HEIGHT = 0;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.POINTY_TOP;

	private CustomGridLayoutStrategy target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
		target = new CustomGridLayoutStrategy();
	}

	@Test
	public void shouldProperlyCreateEmptyGrid() {
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertTrue(hexagons.isEmpty());
	}

	@Test
	public void testCheckParameters() {
		target.checkParameters(0, 0);
	}

}
