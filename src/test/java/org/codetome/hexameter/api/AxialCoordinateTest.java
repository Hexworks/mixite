package org.codetome.hexameter.api;

import static junit.framework.Assert.assertEquals;

import org.codetome.hexameter.api.AxialCoordinate;
import org.junit.Before;
import org.junit.Test;

public class AxialCoordinateTest {

	private static final int TEST_GRID_X = 4;
	private static final int TEST_GRID_Z = 5;
	private AxialCoordinate target;

	@Before
	public void setUp() {
		target = new AxialCoordinate(TEST_GRID_X, TEST_GRID_Z);
	}

	@Test
	public void shouldReturnProperCoordinateWhenGetGridXIsCalled() {
		assertEquals(TEST_GRID_X, target.getGridX());
	}

	@Test
	public void shouldReturnProperCoordinateWhenGetGridZIsCalled() {
		assertEquals(TEST_GRID_Z, target.getGridZ());
	}

}
