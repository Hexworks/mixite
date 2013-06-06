package biz.pavonis.hexameter;

import static junit.framework.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.exception.HexagonalGridCreationException;

public class HexagonGridBuilderTest {

	private static final int GRID_HEIGHT = 9;
	private static final HexagonGridLayout GRID_LAYOUT = HexagonGridLayout.RECTANGULAR;
	private static final GridLayoutStrategy GRID_LAYOUT_STRATEGY = HexagonGridLayout.RECTANGULAR.getGridLayoutStrategy();
	private static final int GRID_WIDTH = 9;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
	private static final double RADIUS = 30;

	private HexagonalGridBuilder target;

	@Before
	public void setUp() {
		target = new HexagonalGridBuilder();
		target.setGridHeight(GRID_HEIGHT).setGridLayout(GRID_LAYOUT).setGridWidth(GRID_WIDTH).setOrientation(ORIENTATION).setRadius(RADIUS).build();
	}

	@Test
	public void testSetters() {
		assertEquals(GRID_HEIGHT, target.getGridHeight());
		assertEquals(GRID_WIDTH, target.getGridWidth());
		assertEquals(GRID_LAYOUT_STRATEGY, target.getGridLayoutStrategy());
		assertEquals(RADIUS, target.getRadius());
		Assert.assertNotNull(target.getSharedHexagonData());
	}

	@Test(expected = IllegalStateException.class)
	public void testFailedSharedHexagonDataWhenNoOrientation() {
		target.setOrientation(null);
		target.getSharedHexagonData();
	}

	@Test(expected = IllegalStateException.class)
	public void testFailedSharedHexagonDataWhenNoRadius() {
		target.setRadius(0);
		target.getSharedHexagonData();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenNoWidth() {
		target.setGridWidth(0);
		target.build();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenNoHeight() {
		target.setGridHeight(0);
		target.build();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenNoOrientation() {
		target.setOrientation(null);
		target.build();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenNoRadius() {
		target.setRadius(0);
		target.build();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenNoLayout() {
		target.setGridLayout(null);
		target.build();
	}

	@Test(expected = HexagonalGridCreationException.class)
	public void testFailedWhenBadLayout() {
		target.setGridLayout(HexagonGridLayout.TRIANGULAR);
		target.setGridHeight(4);
		target.build();
	}

}
