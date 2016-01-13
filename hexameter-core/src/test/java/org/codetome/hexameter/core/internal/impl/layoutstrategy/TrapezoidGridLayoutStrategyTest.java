package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.junit.Before;
import org.junit.Test;

public class TrapezoidGridLayoutStrategyTest {

	private HexagonalGridBuilder builder;
	private TrapezoidGridLayoutStrategy target;

	@Before
	public void setUp() throws Exception {
		builder = GridLayouStrategyTestUtil.fetchDefaultBuilder();
		target = new TrapezoidGridLayoutStrategy();
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
		assertTrue(coords.contains(fromCoordinates(2, 1).toKey()));
		assertTrue(coords.contains(fromCoordinates(2, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(1, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 2).toKey()));
		assertTrue(coords.contains(fromCoordinates(0, 1).toKey()));

		assertTrue(!coords.contains(fromCoordinates(-1, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, -1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 0).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 1).toKey()));
		assertTrue(!coords.contains(fromCoordinates(3, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(2, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(1, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(0, 3).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 2).toKey()));
		assertTrue(!coords.contains(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersIsCalled() {
		assertTrue(target.checkParameters(2, 2));
	}

}
