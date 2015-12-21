package org.codetome.hexameter.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;

import java.util.HashMap;
import java.util.Map;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

public class AbstractGridLayoutStrategyTest {

	private AbstractGridLayoutStrategy target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = fetchDefaultBuilder();
		target = new AbstractGridLayoutStrategy() {

			@Override
            public Map<String, Hexagon> createHexagons(final HexagonalGridBuilder builder) {
				return new HashMap<> ();
			}
		};
	}

	@Test
	public void shouldProperlyCreateHexagonsWhenCreateHexagonsIsCalledWithDefaults() {
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		assertTrue(hexagons.isEmpty());
	}

	@Test
	public void shouldProperlyCreateHexagonsWhenCreateHexagonsIsCalledWithCustomCoordinate() {
		builder.addCustomAxialCoordinate(fromCoordinates(2, 3));
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		target.addCustomHexagons(builder, hexagons);
		assertTrue(hexagons.size() == 1);
		final Hexagon hex = hexagons.get(fromCoordinates(2, 3).toKey());
		assertNotNull(hex);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenCalledWithWrongParameters() {
		target = new RectangularGridLayoutStrategy();
		builder.setGridHeight(100).setGridWidth(100);
		builder.addCustomAxialCoordinate(fromCoordinates(2, 3));
		final Map<String, Hexagon> hexagons = target.createHexagons(builder);
		target.addCustomHexagons(builder, hexagons);
		assertTrue(hexagons.size() == 1);
		final Hexagon hex = hexagons.get(fromCoordinates(2, 3).toKey());
		assertNotNull(hex);
	}

	@Test
	public void testCheckParameters0() {
		final boolean result = target.checkParameters(1, 0);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters1() {
		final boolean result = target.checkParameters(0, 1);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters2() {
		final boolean result = target.checkParameters(0, 0);
		assertFalse(result);
	}

	@Test
	public void testCheckParameters3() {
		final boolean result = target.checkParameters(1, 1);
		assertTrue(result);
	}

}
