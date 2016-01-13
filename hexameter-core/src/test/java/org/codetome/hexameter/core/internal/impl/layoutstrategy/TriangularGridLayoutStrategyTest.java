package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.junit.Before;
import org.junit.Test;

public class TriangularGridLayoutStrategyTest {

	private HexagonalGridBuilder builder;
	private TriangularGridLayoutStrategy target;

	@Before
	public void setUp() throws Exception {
		builder = fetchDefaultBuilder();
		target = new TriangularGridLayoutStrategy();
	}

	@Test
	public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
		final Collection<Hexagon> hexagons = target.createHexagons(builder);
		testHexagons(hexagons);
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
		builder.setOrientation(HexagonOrientation.FLAT_TOP);
		final Collection<Hexagon> hexagons = target.createHexagons(builder);
		testHexagons(hexagons);
	}

	private void testHexagons(final Collection<Hexagon> hexagons) {

	    final Set<String> coords = new HashSet<>();
	    hexagons.forEach(hex -> coords.add(hex.getAxialCoordinate().toKey()));

		assertTrue(coords.contains(fromCoordinates(0, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 0).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 2).toKey()));

		assertTrue(!coords.contains(fromCoordinates(-1, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, 1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersWithOneAndOne() {
		final boolean result = target.checkParameters(1, 1); // super: true, derived: true
		assertTrue(result);
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersWithOneAndTwo() {
		final boolean result = target.checkParameters(1, 2); // super: true, derived: false
		assertFalse(result);
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersWithZeroAndZero() {
		final boolean result = target.checkParameters(0, 0); // super: false, derived: false;
		assertFalse(result);
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersWithMinusOneAndMinusOne() {
		final boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
		assertFalse(result);
	}
}
