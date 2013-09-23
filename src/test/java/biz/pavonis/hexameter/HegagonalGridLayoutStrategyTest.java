package biz.pavonis.hexameter;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.HexagonalGridLayout;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy;



public class HegagonalGridLayoutStrategyTest {

	private static final int GRID_HEIGHT = 9;
	private static final HexagonalGridLayout GRID_LAYOUT = HexagonalGridLayout.RECTANGULAR;
	private static final int GRID_WIDTH = 9;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
	private static final double RADIUS = 30;

	private HexagonalGridLayoutStrategy target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() {
		builder = new HexagonalGridBuilder();
		builder.setGridHeight(GRID_HEIGHT).setGridLayout(GRID_LAYOUT).setGridWidth(GRID_WIDTH).setOrientation(ORIENTATION).setRadius(RADIUS).build();
		target = new HexagonalGridLayoutStrategy();
	}

	@Test
	public void testCreateHexagons() {
		assertEquals(null, target.createHexagons(builder)); // TODO: this will fail after it is implemented
	}

	@Test
	public void testCheckParameters() {
		assertEquals(false, target.checkParameters(0, 0)); // TODO: this will fail after it is implemented
	}
}
