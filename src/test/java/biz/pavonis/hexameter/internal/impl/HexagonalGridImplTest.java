package biz.pavonis.hexameter.internal.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.CoordinateConverter;
import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;

public class HexagonalGridImplTest {

	private static final int GRID_WIDTH = 10;
	private static final int GRID_HEIGHT = 10;
	private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
	private static final int GRID_X_FROM = 2;
	private static final int GRID_X_TO = 4;
	private static final int GRID_Z_FROM = 3;
	private static final int GRID_Z_TO = 5;
	private HexagonalGrid target;
	private HexagonalGridBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(10).setOrientation(ORIENTATION);
		target = builder.build();
	}

	@Test
	public void testHexagonalGridImplWithCustomStorage() {
		Map<String, Hexagon> expected = new HashMap<String, Hexagon>();
		builder.setCustomStorage(expected);
		target = builder.build();
		assertEquals(expected, target.getHexagons());
	}

	@Test
	public void testGetHexagons() {
		Map<String, Hexagon> hexagons = target.getHexagons();
		assertEquals(100, hexagons.size());
		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
				int gridX = CoordinateConverter.convertOffsetCoordinatesToAxialX(x, y, ORIENTATION);
				int gridY = CoordinateConverter.convertOffsetCoordinatesToAxialZ(x, y, ORIENTATION);
				String key = CoordinateConverter.createKeyFromCoordinate(gridX, gridY);
				assertTrue(hexagons.containsKey(key));
				Hexagon hex = hexagons.get(key);
				assertTrue(hex.getGridX() == gridX);
				assertTrue(hex.getGridZ() == gridY);
			}
		}
	}

	@Test
	public void testGetHexagonsByAxialRange() {
		Set<Hexagon> expected = new HashSet<Hexagon>();
		expected.add(target.getByGridCoordinate(2, 3));
		expected.add(target.getByGridCoordinate(3, 3));
		expected.add(target.getByGridCoordinate(4, 3));
		expected.add(target.getByGridCoordinate(2, 4));
		expected.add(target.getByGridCoordinate(3, 4));
		expected.add(target.getByGridCoordinate(4, 4));
		expected.add(target.getByGridCoordinate(2, 5));
		expected.add(target.getByGridCoordinate(3, 5));
		expected.add(target.getByGridCoordinate(4, 5));
		Map<String, Hexagon> actual = target.getHexagonsByAxialRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO);
		assertEquals(expected.size(), actual.size());
		for (Hexagon hex : expected) {
			String key = CoordinateConverter.createKeyFromCoordinate(hex.getGridX(), hex.getGridZ());
			assertTrue(actual.containsKey(key));
			assertEquals(hex, actual.get(key));
		}
	}

	@Test
	public void testGetHexagonsByOffsetRange() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddHexagon() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveHexagon() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByGridCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByPixelCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNeighborsOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearSatelliteData() {
		fail("Not yet implemented");
	}

}
