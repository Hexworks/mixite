package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;

public class TriangularGridLayoutStrategyTest {

    private HexagonalGridBuilder builder;
    private TriangularGridLayoutStrategy target;

    @Before
    public void setUp() throws Exception {
        builder = fetchDefaultBuilder();
        target = new TriangularGridLayoutStrategy();
    }

    @Test
    public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        testCoordinates(target.fetchGridCoordinates(builder));
    }

    @Test
    public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(FLAT_TOP);
        testCoordinates(target.fetchGridCoordinates(builder));
    }

    private void testCoordinates(final Collection<AxialCoordinate> coords) {

        assertTrue(coords.contains(fromCoordinates(0, 0)));
        assertTrue(coords.contains(fromCoordinates(1, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 0)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));
        assertTrue(coords.contains(fromCoordinates(1, 1)));
        assertTrue(coords.contains(fromCoordinates(0, 2)));

        assertTrue(!coords.contains(fromCoordinates(-1, 0)));
        assertTrue(!coords.contains(fromCoordinates(0, -1)));
        assertTrue(!coords.contains(fromCoordinates(1, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, 0)));
        assertTrue(!coords.contains(fromCoordinates(2, 1)));
        assertTrue(!coords.contains(fromCoordinates(1, 2)));
        assertTrue(!coords.contains(fromCoordinates(0, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void shouldReturnTrueWhenCheckParametersWithOneAndOne() {
        final boolean result = target.checkParameters(1, 1); // super: true, derived: true
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenCheckParametersWithOneAndTwo() {
        final boolean result = target.checkParameters(1, 2); // super: true, derived: false
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenCheckParametersWithZeroAndZero() {
        final boolean result = target.checkParameters(0, 0); // super: false, derived: false;
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenCheckParametersWithMinusOneAndMinusOne() {
        final boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
        assertFalse(result);
    }
}
