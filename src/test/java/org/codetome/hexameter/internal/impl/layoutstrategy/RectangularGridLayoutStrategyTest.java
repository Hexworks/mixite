package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
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
		final Collection<Hexagon> hexagons = target.createHexagons(builder);

		final Set<String> coords = new HashSet<>();
        hexagons.forEach(hex -> coords.add(hex.getAxialCoordinate().toKey()));

		assertTrue(coords.contains(fromCoordinates(0, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(-1, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 2).toKey()));

		assertTrue(!coords.contains(fromCoordinates(-1, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-2, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-2, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
		builder.setOrientation(FLAT_TOP);
		final Collection<Hexagon> hexagons = target.createHexagons(builder);

		final Set<String> coords = new HashSet<>();
        hexagons.forEach(hex -> coords.add(hex.getAxialCoordinate().toKey()));

		assertTrue(coords.contains(fromCoordinates(0, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, -1).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 2).toKey()));

		assertTrue(!coords.contains(fromCoordinates(-1, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, -2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-2, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-2, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 1).toKey()));
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
