package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

public class HexagonalGridLayoutStrategyTest {

	private HexagonalGridLayoutStrategy target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = fetchDefaultBuilder();
		target = new HexagonalGridLayoutStrategy();
	}

	@Test
	public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(fromCoordinates(1, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 1).toKey()));

		assertNull(hexagons.get(fromCoordinates(0, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
		builder.setGridHeight(5).setGridWidth(5);
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(fromCoordinates(1, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(3, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(3, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(3, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 3).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 4).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 4).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(-1, 4).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(-1, 3).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(-1, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 1).toKey()));

		assertNull(hexagons.get(fromCoordinates(0, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, 1).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, 5).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, 5).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 5).toKey()));
		assertNull(hexagons.get(fromCoordinates(-2, 5).toKey()));
		assertNull(hexagons.get(fromCoordinates(-2, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(-2, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-2, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
		builder.setOrientation(FLAT_TOP);
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(fromCoordinates(1, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 1).toKey()));

		assertNull(hexagons.get(fromCoordinates(0, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
		builder.setGridHeight(5).setGridWidth(5).setOrientation(FLAT_TOP);
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertNotNull(hexagons.get(fromCoordinates(2, -1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(3, -1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(4, -1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(4, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(4, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(3, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 3).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 3).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 3).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 0).toKey()));

		assertNull(hexagons.get(fromCoordinates(0, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, -2).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, -2).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, -2).toKey()));
		assertNull(hexagons.get(fromCoordinates(5, -2).toKey()));
		assertNull(hexagons.get(fromCoordinates(5, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(5, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(5, 1).toKey()));
		assertNull(hexagons.get(fromCoordinates(4, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 4).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void testCheckParameters0() {
		final boolean result = target.checkParameters(1, 1); // super: true, derived: true
		assertTrue(result);
	}

	@Test
	public void testCheckParameters1() {
		final boolean result = target.checkParameters(1, 2); // super: true, derived: false
		assertFalse(result);
	}

	@Test
	public void testCheckParameters2() {
		final boolean result = target.checkParameters(2, 2); // super: true, derived: false
		assertFalse(result);
	}

	@Test
	public void testCheckParameters3() {
		final boolean result = target.checkParameters(0, 0); // super: false, derived: false;
		assertFalse(result);
	}

	@Test
	public void testCheckParameters4() {
		final boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
		assertFalse(result);
	}

}
