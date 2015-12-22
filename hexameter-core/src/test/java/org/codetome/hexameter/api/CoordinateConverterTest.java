package org.codetome.hexameter.api;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.api.AxialCoordinate.fromKey;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static org.codetome.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;

import org.junit.Test;

public class CoordinateConverterTest {

	private static final int TEST_X = 3;
	private static final int TEST_Y = 4;
	private static final String TEST_KEY = "7,8";
	private static final int TEST_GRID_X = 7;
	private static final int TEST_GRID_Z = 8;

	private static final int EXPECTED_AXIAL_X_WITH_POINTY = 1;
	private static final int EXPECTED_AXIAL_X_WITH_FLAT = 3;
	private static final int EXPECTED_AXIAL_Z_WITH_POINTY = 4;
	private static final int EXPECTED_AXIAL_Z_WITH_FLAT = 3;

	@Test(expected=UnsupportedOperationException.class)
	public void shouldThrowExceptionWhenInstantiated() {
	    new CoordinateConverter();
	}


	@Test
	public void shouldConvertOffsetCoordinatesToAxialXWithPointy() {
		final int result = convertOffsetCoordinatesToAxialX(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP);
		assertEquals(EXPECTED_AXIAL_X_WITH_POINTY, result);
	}

	@Test
	public void shouldConvertOffsetCoordinatesToAxialXWithFlat() {
		final int result = convertOffsetCoordinatesToAxialX(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP);
		assertEquals(EXPECTED_AXIAL_X_WITH_FLAT, result);
	}

	@Test
	public void shouldConvertOffsetCoordinatesToAxialZWithPointy() {
		final int result = convertOffsetCoordinatesToAxialZ(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP);
		assertEquals(EXPECTED_AXIAL_Z_WITH_POINTY, result);
	}

	@Test
	public void shouldConvertOffsetCoordinatesToAxialZWithFlat() {
		final int result = convertOffsetCoordinatesToAxialZ(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP);
		assertEquals(EXPECTED_AXIAL_Z_WITH_FLAT, result);
	}

	@Test
	public void shouldCreateKeyFromCoordinate() {
		assertEquals(TEST_KEY, fromCoordinates(TEST_GRID_X, TEST_GRID_Z).toKey());
	}

	@Test
	public void shouldCreateCoordinateFromKey() {
		final AxialCoordinate c = fromKey(TEST_KEY);
		assertEquals(TEST_GRID_X, c.getGridX());
		assertEquals(TEST_GRID_Z, c.getGridZ());
	}
}
