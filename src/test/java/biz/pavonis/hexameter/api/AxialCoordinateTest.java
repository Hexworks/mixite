package biz.pavonis.hexameter.api;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import biz.pavonis.hexameter.categories.UnitTests;

@Category(UnitTests.class)
public class AxialCoordinateTest {

	private static final int TEST_GRID_X = 4;
	private static final int TEST_GRID_Z = 5;
	private AxialCoordinate target;

	@Before
	public void setUp() {
		target = new AxialCoordinate(TEST_GRID_X, TEST_GRID_Z);
	}

	@Test
	public void testGetGridX() {
		assertEquals(TEST_GRID_X, target.getGridX());
	}

	@Test
	public void testGetGridZ() {
		assertEquals(TEST_GRID_Z, target.getGridZ());
	}

}
