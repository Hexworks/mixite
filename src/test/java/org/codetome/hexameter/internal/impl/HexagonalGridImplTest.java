package org.codetome.hexameter.internal.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static org.codetome.hexameter.api.CoordinateConverter.createKeyFromCoordinate;
import static org.codetome.hexameter.api.HexagonOrientation.POINTY_TOP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.api.exception.HexagonNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class HexagonalGridImplTest {

	private static final int RADIUS = 30;
	private static final int GRID_WIDTH = 10;
	private static final int GRID_HEIGHT = 10;
	private static final HexagonOrientation ORIENTATION = POINTY_TOP;
	private static final int GRID_X_FROM = 2;
	private static final int GRID_X_TO = 4;
	private static final int GRID_Z_FROM = 3;
	private static final int GRID_Z_TO = 5;
	private HexagonalGrid target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
		target = builder.build();
	}

	@Test
	public void shouldReturnProperHexagonsWhenCustomStorageIsSet() {
		final Map<String, Hexagon> expected = new HashMap<> ();
		builder.setCustomStorage(expected);
		target = builder.build();
		assertEquals(expected, target.getHexagons());
	}

	@Test
	public void shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid() {
		final Map<String, Hexagon> hexagons = target.getHexagons();
		assertEquals(100, hexagons.size());
		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
				final int gridX = convertOffsetCoordinatesToAxialX(x, y, ORIENTATION);
				final int gridY = convertOffsetCoordinatesToAxialZ(x, y, ORIENTATION);
				final String key = createKeyFromCoordinate(gridX, gridY);
				assertTrue(hexagons.containsKey(key));
				final Hexagon hex = hexagons.get(key);
				assertTrue(hex.getGridX() == gridX);
				assertTrue(hex.getGridZ() == gridY);
			}
		}
	}

	@Test
	public void shouldReturnProperHexagonsWhenGetHexagonsByAxialRangeIsCalled() {
		final Set<Hexagon> expected = new HashSet<> ();

		expected.add(target.getByGridCoordinate(2, 3));
		expected.add(target.getByGridCoordinate(3, 3));
		expected.add(target.getByGridCoordinate(4, 3));

		expected.add(target.getByGridCoordinate(2, 4));
		expected.add(target.getByGridCoordinate(3, 4));
		expected.add(target.getByGridCoordinate(4, 4));

		expected.add(target.getByGridCoordinate(2, 5));
		expected.add(target.getByGridCoordinate(3, 5));
		expected.add(target.getByGridCoordinate(4, 5));

		final Map<String, Hexagon> actual = target.getHexagonsByAxialRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO);
		assertEquals(expected.size(), actual.size());
		for (final Hexagon hex : expected) {
			final String key = createKeyFromCoordinate(hex.getGridX(), hex.getGridZ());
			assertTrue(actual.containsKey(key));
			assertEquals(hex, actual.get(key));
		}
	}

	@Test
	public void shouldReturnProperHexagonsWhenGetHexagonsByOffsetRangeIsCalled() {
		final Set<Hexagon> expected = new HashSet<> ();

		expected.add(target.getByGridCoordinate(1, 3));
		expected.add(target.getByGridCoordinate(2, 3));
		expected.add(target.getByGridCoordinate(3, 3));

		expected.add(target.getByGridCoordinate(0, 4));
		expected.add(target.getByGridCoordinate(1, 4));
		expected.add(target.getByGridCoordinate(2, 4));

		expected.add(target.getByGridCoordinate(0, 5));
		expected.add(target.getByGridCoordinate(1, 5));
		expected.add(target.getByGridCoordinate(2, 5));

		final Map<String, Hexagon> actual = target.getHexagonsByOffsetRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO);
		assertEquals(expected.size(), actual.size());
		for (final Hexagon hex : expected) {
			final String key = createKeyFromCoordinate(hex.getGridX(), hex.getGridZ());
			assertTrue(actual.containsKey(key));
			assertEquals(hex, actual.get(key));
		}
	}

	@Test
	public void shouldProperlyAddHexagonWhenAddHexagonIsCalled() {
		final int gridX = 20;
		final int gridZ = 30;
		assertFalse(target.containsCoordinate(gridX, gridZ));
		target.addHexagon(gridX, gridZ);
		assertTrue(target.containsCoordinate(gridX, gridZ));
	}

	@Test
	public void shouldProperlyRemoveHexagonWhenRemoveHexagonIsCalled() {
		final int gridX = 2;
		final int gridZ = 3;
		assertTrue(target.containsCoordinate(gridX, gridZ));
		target.removeHexagon(gridX, gridZ);
		assertFalse(target.containsCoordinate(gridX, gridZ));
	}

	@Test
	public void shouldContainCoordinateWhenContainsCoorinateIsCalledWithProperParameters() {
		final int gridX = 2;
		final int gridZ = 3;
		assertTrue(target.containsCoordinate(gridX, gridZ));
	}

	@Test
	public void shouldReturnHexagonWhenGetByGridCoordinateIsCalledWithProperCoordinates() {
		final int gridX = 2;
		final int gridZ = 3;
		final Hexagon hex = target.getByGridCoordinate(gridX, gridZ);
		assertNotNull(hex);
	}

	@Test(expected = HexagonNotFoundException.class)
	public void shouldThrowExceptionHexagonWhenGetByGridCoordinateIsCalledWithInvalidCoordinates() {
		final int gridX = 20;
		final int gridZ = 30;
		target.getByGridCoordinate(gridX, gridZ);
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates() {
		final double x = 310;
		final double y = 255;
		final Hexagon hex = target.getByPixelCoordinate(x, y);
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates2() {
		final double x = 300;
		final double y = 275;
		final Hexagon hex = target.getByPixelCoordinate(x, y);
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates3() {
		final double x = 325;
		final double y = 275;
		final Hexagon hex = target.getByPixelCoordinate(x, y);
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnProperNeighborsOfHexagonWhenHexIsInMiddle() {
		final Hexagon hex = target.getByGridCoordinate(3, 7);
		final Set<Hexagon> expected = new HashSet<> ();
		expected.add(target.getByGridCoordinate(3, 6));
		expected.add(target.getByGridCoordinate(4, 6));
		expected.add(target.getByGridCoordinate(4, 7));
		expected.add(target.getByGridCoordinate(3, 8));
		expected.add(target.getByGridCoordinate(2, 8));
		expected.add(target.getByGridCoordinate(2, 7));
		final Set<Hexagon> actual = target.getNeighborsOf(hex);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge() {
		final Hexagon hex = target.getByGridCoordinate(5, 9);
		final Set<Hexagon> expected = new HashSet<> ();
		expected.add(target.getByGridCoordinate(5, 8));
		expected.add(target.getByGridCoordinate(4, 9));
		final Set<Hexagon> actual = target.getNeighborsOf(hex);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldProperlyClearSatelliteDataWhenClearSatelliteDataIsCalled() {
		final Hexagon testHex = target.getByGridCoordinate(2, 3);
		final Object data = new Object();
		testHex.setSatelliteData(data);
		target.clearSatelliteData();
		assertTrue(testHex.getSatelliteData() == null);
	}
}
