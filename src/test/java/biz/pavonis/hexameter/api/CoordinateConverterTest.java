package biz.pavonis.hexameter.api;

import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialX;
import static biz.pavonis.hexameter.api.CoordinateConverter.convertOffsetCoordinatesToAxialZ;
import static biz.pavonis.hexameter.api.CoordinateConverter.createCoordinateFromKey;
import static biz.pavonis.hexameter.api.CoordinateConverter.createKeyFromCoordinate;
import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import biz.pavonis.hexameter.categories.UnitTests;

@Category(UnitTests.class)
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

	@Test
	public void testConvertOffsetCoordinatesToAxialXWithPointy() {
		int result = convertOffsetCoordinatesToAxialX(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP);
		assertEquals(EXPECTED_AXIAL_X_WITH_POINTY, result);
	}

	@Test
	public void testConvertOffsetCoordinatesToAxialXWithFlat() {
		int result = convertOffsetCoordinatesToAxialX(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP);
		assertEquals(EXPECTED_AXIAL_X_WITH_FLAT, result);
	}

	@Test
	public void testConvertOffsetCoordinatesToAxialZWithPointy() {
		int result = convertOffsetCoordinatesToAxialZ(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP);
		assertEquals(EXPECTED_AXIAL_Z_WITH_POINTY, result);
	}

	@Test
	public void testConvertOffsetCoordinatesToAxialZWithFlat() {
		int result = convertOffsetCoordinatesToAxialZ(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP);
		assertEquals(EXPECTED_AXIAL_Z_WITH_FLAT, result);
	}

	@Test
	public void testCreateKeyFromCoordinate() {
		assertEquals(TEST_KEY, createKeyFromCoordinate(TEST_GRID_X, TEST_GRID_Z));
	}

	@Test
	public void testCreateCoordinateFromKey() {
		AxialCoordinate c = createCoordinateFromKey(TEST_KEY);
		assertEquals(TEST_GRID_X, c.getGridX());
		assertEquals(TEST_GRID_Z, c.getGridZ());
	}
}
