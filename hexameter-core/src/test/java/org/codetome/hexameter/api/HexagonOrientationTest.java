package org.codetome.hexameter.api;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.testutils.TestUtils.superficialEnumCodeCoverage;

import org.codetome.hexameter.api.HexagonOrientation;
import org.junit.Test;

public class HexagonOrientationTest {

	@Test
	public void testEnum() {
		superficialEnumCodeCoverage(HexagonOrientation.class);
	}

	@Test
	public void shouldProperlyCalculateFlatCoordinateOffset() {
		assertEquals(0.0f, FLAT_TOP.getCoordinateOffset());
	}

	@Test
	public void shouldProperlyCalculatePointyCoordinateOffset() {
		assertEquals(0.5f, POINTY_TOP.getCoordinateOffset());
	}
}
