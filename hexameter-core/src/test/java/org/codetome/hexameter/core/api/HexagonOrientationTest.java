package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.testutils.TestUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;

public class HexagonOrientationTest {

    @Test
    public void testEnum() {
        TestUtils.superficialEnumCodeCoverage(HexagonOrientation.class);
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
