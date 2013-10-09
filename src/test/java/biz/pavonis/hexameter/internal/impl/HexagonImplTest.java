package biz.pavonis.hexameter.internal.impl;

import static java.lang.Math.round;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.Point;
import biz.pavonis.hexameter.internal.SharedHexagonData;

public class HexagonImplTest {

	private static final double TEST_RADIUS = 10;
	private static final SharedHexagonData TEST_POINTY_DATA = new SharedHexagonData(HexagonOrientation.POINTY_TOP, TEST_RADIUS);
	private static final SharedHexagonData TEST_FLAT_DATA = new SharedHexagonData(HexagonOrientation.FLAT_TOP, TEST_RADIUS);
	private static final int TEST_GRID_X = 2;
	private static final int TEST_GRID_Z = 3;
	private static final int TEST_GRID_Y = -5;
	private static final Object TEST_SATELLITE_DATA = new Object();
	private static final int EXPECTED_POINTY_CENTER_X = 69;
	private static final int EXPECTED_FLAT_CENTER_X = 40;
	private static final int EXPECTED_POINTY_CENTER_Y = 55;
	private static final int EXPECTED_FLAT_CENTER_Y = 78;
	private static final Point[] EXPECTED_FLAT_POINTS = new Point[] { new Point(50, 78), new Point(45, 87), new Point(35, 87), new Point(30, 78), new Point(35, 69),
			new Point(45, 69) };
	private static final Point[] EXPECTED_POINTY_POINTS = new Point[] { new Point(78, 60), new Point(69, 65), new Point(61, 60), new Point(61, 50), new Point(69, 45),
			new Point(78, 50) };

	private Hexagon target;

	@Before
	public void setUp() {
		target = new HexagonImpl(TEST_POINTY_DATA, TEST_GRID_X, TEST_GRID_Z);
	}

	@Test
	public void testGetPointsPointy() {
		for (int i = 0; i < 6; i++) {
			assertEquals((int) EXPECTED_POINTY_POINTS[i].x, (int) round(target.getPoints()[i].x));
			assertEquals((int) EXPECTED_POINTY_POINTS[i].y, (int) round(target.getPoints()[i].y));
		}
	}

	@Test
	public void testGetPointsFlat() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_GRID_X, TEST_GRID_Z);
		for (int i = 0; i < 6; i++) {
			assertEquals((int) EXPECTED_FLAT_POINTS[i].x, (int) round(target.getPoints()[i].x));
			assertEquals((int) EXPECTED_FLAT_POINTS[i].y, (int) round(target.getPoints()[i].y));
		}
	}

	@Test
	public void testSetSatelliteData() {
		target.setSatelliteData(TEST_SATELLITE_DATA);
		assertEquals(TEST_SATELLITE_DATA, target.getSatelliteData());
	}

	@Test
	public void testGetGridX() {
		assertEquals(TEST_GRID_X, target.getGridX());
	}

	@Test
	public void testGetGridY() {
		assertEquals(TEST_GRID_Y, target.getGridY());
	}

	@Test
	public void testGetGridZ() {
		assertEquals(TEST_GRID_Z, target.getGridZ());
	}

	@Test
	public void testGetCenterXPointy() {
		assertEquals(EXPECTED_POINTY_CENTER_X, round(target.getCenterX()));
	}

	@Test
	public void testGetCenterXFlat() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_GRID_X, TEST_GRID_Z);
		assertEquals(EXPECTED_FLAT_CENTER_X, round(target.getCenterX()));
	}

	@Test
	public void testGetCenterYPointy() {
		assertEquals(EXPECTED_POINTY_CENTER_Y, round(target.getCenterY()));
	}

	@Test
	public void testGetCenterYFlat() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_GRID_X, TEST_GRID_Z);
		assertEquals(EXPECTED_FLAT_CENTER_Y, round(target.getCenterY()));
	}

}
