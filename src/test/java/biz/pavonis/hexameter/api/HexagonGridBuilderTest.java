package biz.pavonis.hexameter.api;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import biz.pavonis.hexameter.api.exception.HexagonalGridCreationException;
import biz.pavonis.hexameter.categories.UnitTests;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;

@Category(UnitTests.class)
public class HexagonGridBuilderTest {

	private static final int GRID_HEIGHT = 9;
	private static final HexagonalGridLayout GRID_LAYOUT = HexagonalGridLayout.RECTANGULAR;
	private static final GridLayoutStrategy GRID_LAYOUT_STRATEGY = HexagonalGridLayout.RECTANGULAR.getGridLayoutStrategy();
	private static final int GRID_WIDTH = 9;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
	private static final double RADIUS = 30;

	private HexagonalGridBuilder target;

	@Before
	public void setUp() {
		target = new HexagonalGridBuilder();
		target.setGridHeight(GRID_HEIGHT).setGridLayout(GRID_LAYOUT).setGridWidth(GRID_WIDTH).setOrientation(ORIENTATION).setRadius(RADIUS);
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
		target.setGridLayout(HexagonalGridLayout.TRIANGULAR);
		target.setGridHeight(4);
		target.build();
	}

	@Test
	public void testAddCustomCoordinate() {
		int gridX = 1;
		int gridZ = 2;
		int size = target.getCustomCoordinates().size();
		target.addCustomAxialCoordinate(new AxialCoordinate(gridX, gridZ));
		assertTrue(target.getCustomCoordinates().size() == size + 1);
	}

	@Test
	public void testSetCustomStorage() {
		Map<String, Hexagon> customStorage = new HashMap<String, Hexagon>();
		target.setCustomStorage(customStorage);
		assertEquals(customStorage, target.getCustomStorage());
	}

	@Test
	public void testBuildCalculatorFor() {
		HexagonalGridCalculator calc = target.buildCalculatorFor(null);
		Assert.assertNotNull(calc);
		assertTrue(calc instanceof HexagonalGridCalculator);
	}

	@Test
	public void testGetOrientation() {
		assertEquals(ORIENTATION, target.getOrientation());
	}

	@Test
	public void testBuild() {
		HexagonalGrid grid = target.build();
		assertNotNull(grid);
	}

}
