package biz.pavonis.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.AxialCoordinate;
import biz.pavonis.hexameter.api.CoordinateConverter;
import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;

public class AbstractGridLayoutStrategyTest {

	private static final int RADIUS = 30;
	private static final int GRID_WIDTH = 0;
	private static final int GRID_HEIGHT = 0;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.POINTY_TOP;

	private AbstractGridLayoutStrategy target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
		target = new AbstractGridLayoutStrategy() {

			public Map<String, Hexagon> createHexagons(HexagonalGridBuilder builder) {
				return new HashMap<String, Hexagon>();
			}
		};
	}

	@Test
	public void testCreateHexagonsWithNoCustom() {
		Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertTrue(hexagons.isEmpty());
	}

	@Test
	public void testCreateHexagonsWithCustom() {
		builder.addCustomAxialCoordinate(new AxialCoordinate(2, 3));
		Map<String, Hexagon> hexagons = target.createHexagons(builder);
		target.addCustomHexagons(builder, hexagons);
		assertTrue(hexagons.size() == 1);
		Hexagon hex = hexagons.get(CoordinateConverter.createKeyFromCoordinate(2, 3));
		assertNotNull(hex);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateHexagonsWithInvalidCustom() {
		target = new RectangularGridLayoutStrategy();
		builder.setGridHeight(100).setGridWidth(100);
		builder.addCustomAxialCoordinate(new AxialCoordinate(2, 3));
		Map<String, Hexagon> hexagons = target.createHexagons(builder);
		target.addCustomHexagons(builder, hexagons);
		assertTrue(hexagons.size() == 1);
		Hexagon hex = hexagons.get(CoordinateConverter.createKeyFromCoordinate(2, 3));
		assertNotNull(hex);
	}

	@Test
	public void testCheckParameters0() {
		boolean result = target.checkParameters(1, 0);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters1() {
		boolean result = target.checkParameters(0, 1);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters2() {
		boolean result = target.checkParameters(0, 0);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters3() {
		boolean result = target.checkParameters(1, 1);
		assertTrue(result);
	}

}
