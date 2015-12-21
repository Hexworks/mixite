package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
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
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		testHexagons(hexagons);
	}

	@Test
	public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
		builder.setOrientation(FLAT_TOP);
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		testHexagons(hexagons);
	}

	private void testHexagons(final Map<String, Hexagon> hexagons) {
		assertNotNull(hexagons.get(fromCoordinates(0, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 0).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 1).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(2, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(1, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 2).toKey()));
		assertNotNull(hexagons.get(fromCoordinates(0, 1).toKey()));

		assertNull(hexagons.get(fromCoordinates(-1, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, -1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 0).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 1).toKey()));
		assertNull(hexagons.get(fromCoordinates(3, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(2, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(1, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(0, 3).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 2).toKey()));
		assertNull(hexagons.get(fromCoordinates(-1, 1).toKey()));
	}

	@Test
	public void shouldReturnTrueWhenCheckParametersIsCalled() {
		assertTrue(target.checkParameters(2, 2));
	}

}
