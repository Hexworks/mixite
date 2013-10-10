package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;

public class HexagonalGridLayoutStrategyTest {

	private static final int RADIUS = 30;
	private static final int GRID_WIDTH = 10;
	private static final int GRID_HEIGHT = 10;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.POINTY_TOP;

	private HexagonalGridLayoutStrategy target;
	@SuppressWarnings("unused")
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
		target = new HexagonalGridLayoutStrategy();
	}

	@Test
	public void testCreateHexagons() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckParameters0() {
		boolean result = target.checkParameters(1, 1); // super: true, derived: true
		assertTrue(result);
	}

	@Test
	public void testCheckParameters1() {
		boolean result = target.checkParameters(1, 2); // super: true, derived: false
		assertFalse(result);
	}

	@Test
	public void testCheckParameters2() {
		boolean result = target.checkParameters(2, 2); // super: true, derived: false
		assertFalse(result);
	}

	@Test
	public void testCheckParameters3() {
		boolean result = target.checkParameters(0, 0); // super: false, derived: false;
		assertFalse(result);
	}

	@Test
	public void testCheckParameters4() {
		boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
		assertFalse(result);
	}

}
