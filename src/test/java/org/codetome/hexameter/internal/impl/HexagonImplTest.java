package org.codetome.hexameter.internal.impl;

import static java.lang.Math.round;
import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonOrientation;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.internal.SharedHexagonData;
import org.junit.Before;
import org.junit.Test;

public class HexagonImplTest {

	private static final double TEST_RADIUS = 10;
	private static final SharedHexagonData TEST_POINTY_DATA = new SharedHexagonData(HexagonOrientation.POINTY_TOP, TEST_RADIUS);
	private static final SharedHexagonData TEST_FLAT_DATA = new SharedHexagonData(HexagonOrientation.FLAT_TOP, TEST_RADIUS);
	private static final int TEST_GRID_X = 2;
	private static final int TEST_GRID_Z = 3;
	private static final AxialCoordinate TEST_COORDINATE = fromCoordinates(TEST_GRID_X, TEST_GRID_Z);
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
		target = new HexagonImpl(TEST_POINTY_DATA, TEST_COORDINATE);
	}

	@Test
	public void shouldReturnProperStringRepresentationOfHexagonWhenToStringCalled() {
	    assertEquals("HexagonImpl#{x=" + TEST_GRID_X + ", z=" + TEST_GRID_Z + "}", target.toString());
	}

	@Test
	public void shouldHaveProperPointsWhenPointy() {
		for (int i = 0; i < 6; i++) {
			assertEquals((int) EXPECTED_POINTY_POINTS[i].x, (int) round(target.getPoints()[i].x));
			assertEquals((int) EXPECTED_POINTY_POINTS[i].y, (int) round(target.getPoints()[i].y));
		}
	}

	@Test
	public void shouldHaveProperPointsWhenFlat() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE);
		for (int i = 0; i < 6; i++) {
			assertEquals((int) EXPECTED_FLAT_POINTS[i].x, (int) round(target.getPoints()[i].x));
			assertEquals((int) EXPECTED_FLAT_POINTS[i].y, (int) round(target.getPoints()[i].y));
		}
	}

	@Test
	public void shouldReturnProperSatelliteDataWhenSatelliteDataIsSet() {
		target.setSatelliteData(TEST_SATELLITE_DATA);
		assertEquals(TEST_SATELLITE_DATA, target.getSatelliteData());
	}

	@Test
	public void shouldReturnProperXCoordinateWhenGetGridXIsCalled() {
		assertEquals(TEST_GRID_X, target.getGridX());
	}

	@Test
	public void shouldReturnProperXCoordinateWhenGetGridYIsCalled() {
		assertEquals(TEST_GRID_Y, target.getGridY());
	}

	@Test
	public void shouldReturnProperXCoordinateWhenGetGridZIsCalled() {
		assertEquals(TEST_GRID_Z, target.getGridZ());
	}

	@Test
	public void shouldReturnProperCenterXCoordinateWhenGetCenterXIsCalledWithPointyHexagons() {
		assertEquals(EXPECTED_POINTY_CENTER_X, round(target.getCenterX()));
	}

	@Test
	public void shouldReturnProperCenterXCoordinateWhenGetCenterXIsCalledWithFlatHexagons() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE);
		assertEquals(EXPECTED_FLAT_CENTER_X, round(target.getCenterX()));
	}

	@Test
	public void shouldReturnProperCenterYCoordinateWhenGetCenterYIsCalledWithPointyHexagons() {
		assertEquals(EXPECTED_POINTY_CENTER_Y, round(target.getCenterY()));
	}

	@Test
	public void shouldReturnProperCenterYCoordinateWhenGetCenterYIsCalledWithFlatHexagons() {
		target = new HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE);
		assertEquals(EXPECTED_FLAT_CENTER_Y, round(target.getCenterY()));
	}

}
