package biz.pavonis.hexameter.internal.impl;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.internal.SharedHexagonData;

public class HexagonImplTest {

	private static final HexagonOrientation orientation = HexagonOrientation.POINTY_TOP;
	private static final double radius = 10;
	private static final SharedHexagonData sharedHexagonData = new SharedHexagonData(orientation, radius);
	private static final int gridX = 2;
	private static final int gridZ = 3;
	private Hexagon target;

	@Before
	public void setUp() {
		target = new HexagonImpl(sharedHexagonData, gridX, gridZ);
	}

	@Test
	public void testHexagonImpl() {
	}

	@Test
	public void testGetPoints() {
	}

	@Test
	public void testGetSatelliteData() {
	}

	@Test
	public void testSetSatelliteData() {
	}

	@Test
	public void testGetGridX() {
	}

	@Test
	public void testGetGridY() {
	}

	@Test
	public void testGetGridZ() {
	}

	@Test
	public void testGetCenterX() {
	}

	@Test
	public void testGetCenterY() {
	}

}
