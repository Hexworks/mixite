package biz.pavonis.hexameter.internal;

import static java.lang.Math.sqrt;
import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.internal.SharedHexagonData;


public class SharedHexagonDataTest {

	private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
	private static final double RADIUS = 30;
	private static final int X = 4;
	private static final int Y = 8;
	SharedHexagonData target;

	@After
	public void tearDown() {
		target = null;
	}

	@Test
	public void testRadiusSet() {
		target = new SharedHexagonData(ORIENTATION, RADIUS);
		assertEquals(RADIUS, target.getRadius());
	}

	@Test
	public void testCalculateWidthWithPointy() {
		target = createWithPointy();
		double expectedWidth = sqrt(3) * RADIUS;
		double actualWidth = target.getWidth();
		assertEquals(expectedWidth, actualWidth);
	}

	@Test
	public void testCalculateWidthWithFlat() {
		target = createWithFlat();
		double expectedWidth = RADIUS * 3 / 2;
		double actualWidth = target.getWidth();
		assertEquals(expectedWidth, actualWidth);
	}

	@Test
	public void testCalculateHeightWithPointy() {
		target = createWithPointy();
		double expectedHeight = RADIUS * 3 / 2;
		double actualHeight = target.getHeight();
		assertEquals(expectedHeight, actualHeight);
	}

	@Test
	public void testCalculateHeightWithFlat() {
		target = createWithFlat();
		double expectedHeight = sqrt(3) * RADIUS;
		double actualHeight = target.getHeight();
		assertEquals(expectedHeight, actualHeight);
	}

	@Test
	public void testGetOrientation() {
		target = new SharedHexagonData(ORIENTATION, RADIUS);
		assertEquals(ORIENTATION, target.getOrientation());
	}

	@Test
	public void testGetCoordinateOffset() {
		target = new SharedHexagonData(ORIENTATION, RADIUS);
		assertEquals(ORIENTATION.getCoordinateOffset(), target.getOrientation().getCoordinateOffset());
	}

	private SharedHexagonData createWithFlat() {
		return new SharedHexagonData(HexagonOrientation.FLAT_TOP, RADIUS);
	}

	private SharedHexagonData createWithPointy() {
		return new SharedHexagonData(HexagonOrientation.POINTY_TOP, RADIUS);
	}
}
