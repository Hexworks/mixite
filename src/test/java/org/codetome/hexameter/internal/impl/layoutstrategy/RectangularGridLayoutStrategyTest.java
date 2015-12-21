package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.CoordinateConverter.createKeyFromCoordinate;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.internal.impl.layoutstrategy.RectangularGridLayoutStrategy;
import org.junit.Before;
import org.junit.Test;

public class RectangularGridLayoutStrategyTest {

	private HexagonalGridBuilder builder;
	RectangularGridLayoutStrategy target;

	@Before
	public void setUp() throws Exception {
		builder = GridLayouStrategyTestUtil.fetchDefaultBuilder();
		target = new RectangularGridLayoutStrategy();
	}

	@Test
	public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(2, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(2, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(-1, 2)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 2)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 2)));

		assertNull(hexagons.get(createKeyFromCoordinate(-1, 0)));
		assertNull(hexagons.get(createKeyFromCoordinate(0, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(1, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(2, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, 0)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, 1)));
		assertNull(hexagons.get(createKeyFromCoordinate(2, 2)));
		assertNull(hexagons.get(createKeyFromCoordinate(1, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(0, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-1, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-2, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-2, 2)));
		assertNull(hexagons.get(createKeyFromCoordinate(-1, 1)));
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
		builder.setOrientation(FLAT_TOP);
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(2, -1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(2, 0)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(2, 1)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(0, 2)));
		assertNotNull(hexagons.get(createKeyFromCoordinate(1, 2)));

		assertNull(hexagons.get(createKeyFromCoordinate(-1, 0)));
		assertNull(hexagons.get(createKeyFromCoordinate(0, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(1, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(2, -2)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, -1)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, 0)));
		assertNull(hexagons.get(createKeyFromCoordinate(3, 1)));
		assertNull(hexagons.get(createKeyFromCoordinate(2, 2)));
		assertNull(hexagons.get(createKeyFromCoordinate(1, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(0, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-1, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-2, 3)));
		assertNull(hexagons.get(createKeyFromCoordinate(-2, 2)));
		assertNull(hexagons.get(createKeyFromCoordinate(-1, 1)));
	}

	@Test
	public void testCheckParameters0() {
		final boolean result = target.checkParameters(1, 1); // super: true, derived: true
		assertTrue(result);
	}

	@Test
	public void testCheckParameters1() {
		final boolean result = target.checkParameters(0, 0); // super: false, derived: false;
		assertFalse(result);
	}

	@Test
	public void testCheckParameters2() {
		final boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
		assertFalse(result);
	}

	@Test
	public void testCheckParameters3() {
		final boolean result = target.checkParameters(1, 2); // super: true, derived: true
		assertTrue(result);
	}

}
