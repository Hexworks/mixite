package org.codetome.hexameter.internal.impl;

import org.codetome.hexameter.api.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.*;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static org.codetome.hexameter.api.HexagonOrientation.POINTY_TOP;

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
		assertTrue(expected.values().containsAll(target.getHexagons()));
	}

	@Test
	public void shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid() {
		final Collection<Hexagon> hexagons = target.getHexagons();
		hexagons.forEach(hexagon -> {
			hexagon.getPoints().forEach(point -> {
				// your draw logic here
			});
		});
		final Set<String> expectedCoordinates = new HashSet<>();
		assertEquals(100, hexagons.size());
		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
				final int gridX = convertOffsetCoordinatesToAxialX(x, y, ORIENTATION);
				final int gridZ = convertOffsetCoordinatesToAxialZ(x, y, ORIENTATION);
				expectedCoordinates.add(gridX + "," + gridZ);
			}
		}
		hexagons.forEach(hexagon -> expectedCoordinates.remove(hexagon.getGridX() + "," + hexagon.getGridZ()));
		assertTrue(expectedCoordinates.isEmpty());
	}

	@Test
	public void shouldReturnProperHexagonsWhenGetHexagonsByAxialRangeIsCalled() {
		final Set<Hexagon> expected = new HashSet<> ();

		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 3)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 3)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 3)).get());

		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 4)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 4)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 4)).get());

		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 5)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 5)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 5)).get());

		final Collection<Hexagon> actual = target.getHexagonsByAxialRange(fromCoordinates(GRID_X_FROM, GRID_Z_FROM), fromCoordinates(GRID_X_TO, GRID_Z_TO));
		assertEquals(expected.size(), actual.size());
		actual.forEach(hex -> expected.remove(hex));
		assertTrue(expected.isEmpty());
	}

	@Test
	public void shouldReturnProperHexagonsWhenGetHexagonsByOffsetRangeIsCalled() {
		final Set<Hexagon> expected = new HashSet<> ();

		expected.add(target.getByAxialCoordinate(fromCoordinates(1, 3)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 3)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 3)).get());

		expected.add(target.getByAxialCoordinate(fromCoordinates(0, 4)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(1, 4)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 4)).get());

		expected.add(target.getByAxialCoordinate(fromCoordinates(0, 5)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(1, 5)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 5)).get());

		final Collection<Hexagon> actual = target.getHexagonsByOffsetRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO);
		assertEquals(expected.size(), actual.size());
        actual.forEach(hex -> expected.remove(hex));
        assertTrue(expected.isEmpty());
	}

	@Test
	public void shouldProperlyAddHexagonWhenAddHexagonIsCalled() {
		final int gridX = 20;
		final int gridZ = 30;
		assertFalse(target.containsAxialCoordinate(fromCoordinates(gridX, gridZ)));
		target.addHexagon(fromCoordinates(gridX, gridZ));
		assertTrue(target.containsAxialCoordinate(fromCoordinates(gridX, gridZ)));
	}

	@Test
	public void shouldProperlyRemoveHexagonWhenRemoveHexagonIsCalled() {
		final int gridX = 2;
		final int gridZ = 3;
		final AxialCoordinate coordinate = fromCoordinates(gridX, gridZ);
		assertTrue(target.containsAxialCoordinate(coordinate));
		target.removeHexagon(coordinate);
		assertFalse(target.containsAxialCoordinate(coordinate));
	}

	@Test
	public void shouldContainCoordinateWhenContainsCoorinateIsCalledWithProperParameters() {
		final int gridX = 2;
		final int gridZ = 3;
		assertTrue(target.containsAxialCoordinate(fromCoordinates(gridX, gridZ)));
	}

	@Test
	public void shouldReturnHexagonWhenGetByGridCoordinateIsCalledWithProperCoordinates() {
		final int gridX = 2;
		final int gridZ = 3;
		final Optional<Hexagon> hex = target.getByAxialCoordinate(fromCoordinates(gridX, gridZ));
		assertTrue(hex.isPresent());
	}

	@Test()
	public void shouldBeEmptyWhenGetByGridCoordinateIsCalledWithInvalidCoordinates() {
		final int gridX = 20;
		final int gridZ = 30;
		Optional<Hexagon> result = target.getByAxialCoordinate(fromCoordinates(gridX, gridZ));
		Assert.assertFalse(result.isPresent());
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates() {
		final double x = 310;
		final double y = 255;
		final Hexagon hex = target.getByPixelCoordinate(x, y).get();
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates2() {
		final double x = 300;
		final double y = 275;
		final Hexagon hex = target.getByPixelCoordinate(x, y).get();
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnHexagonWhenCalledWithProperCoordinates3() {
		final double x = 325;
		final double y = 275;
		final Hexagon hex = target.getByPixelCoordinate(x, y).get();
		assertTrue(hex.getGridX() == 3);
		assertTrue(hex.getGridZ() == 5);
	}

	@Test
	public void shouldReturnProperNeighborsOfHexagonWhenHexIsInMiddle() {
		final Hexagon hex = target.getByAxialCoordinate(fromCoordinates(3, 7)).get();
		final Set<Hexagon> expected = new HashSet<> ();
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 6)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 6)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 7)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(3, 8)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 8)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(2, 7)).get());
		final Collection<Hexagon> actual = target.getNeighborsOf(hex);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge() {
		final Hexagon hex = target.getByAxialCoordinate(fromCoordinates(5, 9)).get();
		final Set<Hexagon> expected = new HashSet<> ();
		expected.add(target.getByAxialCoordinate(fromCoordinates(5, 8)).get());
		expected.add(target.getByAxialCoordinate(fromCoordinates(4, 9)).get());
		final Collection<Hexagon> actual = target.getNeighborsOf(hex);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldProperlyClearSatelliteDataWhenClearSatelliteDataIsCalled() {
		final Hexagon testHex = target.getByAxialCoordinate(fromCoordinates(2, 3)).get();
		final Object data = new Object();
		testHex.setSatelliteData(data);
		target.clearSatelliteData();
		assertTrue(!testHex.getSatelliteData().isPresent());
	}
}
