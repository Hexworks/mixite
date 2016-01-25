package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;

public class HexagonalGridLayoutStrategyTest {

    private HexagonalGridLayoutStrategy target;
    private HexagonalGridBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = fetchDefaultBuilder();
        target = new HexagonalGridLayoutStrategy();
    }

    @Test
    public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        final Collection<AxialCoordinate> coords = target.fetchGridCoordinates(builder);


        assertTrue(coords.contains(fromCoordinates(1, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 1)));
        assertTrue(coords.contains(fromCoordinates(1, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));

        assertTrue(!coords.contains(fromCoordinates(0, 0)));
        assertTrue(!coords.contains(fromCoordinates(1, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, 0)));
        assertTrue(!coords.contains(fromCoordinates(3, 1)));
        assertTrue(!coords.contains(fromCoordinates(2, 2)));
        assertTrue(!coords.contains(fromCoordinates(1, 3)));
        assertTrue(!coords.contains(fromCoordinates(0, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
        builder.setGridHeight(5).setGridWidth(5);
        final Collection<AxialCoordinate> coords = target.fetchGridCoordinates(builder);

        assertTrue(coords.contains(fromCoordinates(1, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 0)));
        assertTrue(coords.contains(fromCoordinates(3, 0)));
        assertTrue(coords.contains(fromCoordinates(3, 1)));
        assertTrue(coords.contains(fromCoordinates(3, 2)));
        assertTrue(coords.contains(fromCoordinates(2, 3)));
        assertTrue(coords.contains(fromCoordinates(1, 4)));
        assertTrue(coords.contains(fromCoordinates(0, 4)));
        assertTrue(coords.contains(fromCoordinates(-1, 4)));
        assertTrue(coords.contains(fromCoordinates(-1, 3)));
        assertTrue(coords.contains(fromCoordinates(-1, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));

        assertTrue(!coords.contains(fromCoordinates(0, 0)));
        assertTrue(!coords.contains(fromCoordinates(1, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, -1)));
        assertTrue(!coords.contains(fromCoordinates(4, -1)));
        assertTrue(!coords.contains(fromCoordinates(4, 0)));
        assertTrue(!coords.contains(fromCoordinates(4, 1)));
        assertTrue(!coords.contains(fromCoordinates(4, 2)));
        assertTrue(!coords.contains(fromCoordinates(3, 3)));
        assertTrue(!coords.contains(fromCoordinates(2, 4)));
        assertTrue(!coords.contains(fromCoordinates(1, 5)));
        assertTrue(!coords.contains(fromCoordinates(0, 5)));
        assertTrue(!coords.contains(fromCoordinates(-1, 5)));
        assertTrue(!coords.contains(fromCoordinates(-2, 5)));
        assertTrue(!coords.contains(fromCoordinates(-2, 4)));
        assertTrue(!coords.contains(fromCoordinates(-2, 3)));
        assertTrue(!coords.contains(fromCoordinates(-2, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(HexagonOrientation.FLAT_TOP);
        final Collection<AxialCoordinate> coords = target.fetchGridCoordinates(builder);


        assertTrue(coords.contains(fromCoordinates(1, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 1)));
        assertTrue(coords.contains(fromCoordinates(1, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));

        assertTrue(!coords.contains(fromCoordinates(0, 0)));
        assertTrue(!coords.contains(fromCoordinates(0, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, 0)));
        assertTrue(!coords.contains(fromCoordinates(3, 1)));
        assertTrue(!coords.contains(fromCoordinates(2, 2)));
        assertTrue(!coords.contains(fromCoordinates(1, 3)));
        assertTrue(!coords.contains(fromCoordinates(0, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
        builder.setGridHeight(5).setGridWidth(5).setOrientation(HexagonOrientation.FLAT_TOP);
        final Collection<AxialCoordinate> coords = target.fetchGridCoordinates(builder);


        assertTrue(coords.contains(fromCoordinates(2, -1)));
        assertTrue(coords.contains(fromCoordinates(3, -1)));
        assertTrue(coords.contains(fromCoordinates(4, -1)));
        assertTrue(coords.contains(fromCoordinates(4, 0)));
        assertTrue(coords.contains(fromCoordinates(4, 1)));
        assertTrue(coords.contains(fromCoordinates(3, 2)));
        assertTrue(coords.contains(fromCoordinates(2, 3)));
        assertTrue(coords.contains(fromCoordinates(1, 3)));
        assertTrue(coords.contains(fromCoordinates(0, 3)));
        assertTrue(coords.contains(fromCoordinates(0, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));
        assertTrue(coords.contains(fromCoordinates(1, 0)));

        assertTrue(!coords.contains(fromCoordinates(0, 0)));
        assertTrue(!coords.contains(fromCoordinates(1, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -2)));
        assertTrue(!coords.contains(fromCoordinates(3, -2)));
        assertTrue(!coords.contains(fromCoordinates(4, -2)));
        assertTrue(!coords.contains(fromCoordinates(5, -2)));
        assertTrue(!coords.contains(fromCoordinates(5, -1)));
        assertTrue(!coords.contains(fromCoordinates(5, 0)));
        assertTrue(!coords.contains(fromCoordinates(5, 1)));
        assertTrue(!coords.contains(fromCoordinates(4, 2)));
        assertTrue(!coords.contains(fromCoordinates(3, 3)));
        assertTrue(!coords.contains(fromCoordinates(2, 4)));
        assertTrue(!coords.contains(fromCoordinates(1, 4)));
        assertTrue(!coords.contains(fromCoordinates(0, 4)));
        assertTrue(!coords.contains(fromCoordinates(-1, 4)));
        assertTrue(!coords.contains(fromCoordinates(-1, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void testCheckParameters0() {
        final boolean result = target.checkParameters(1, 1); // super: true, derived: true
        assertTrue(result);
    }

    @Test
    public void testCheckParameters1() {
        final boolean result = target.checkParameters(1, 2); // super: true, derived: false
        assertFalse(result);
    }

    @Test
    public void testCheckParameters2() {
        final boolean result = target.checkParameters(2, 2); // super: true, derived: false
        assertFalse(result);
    }

    @Test
    public void testCheckParameters3() {
        final boolean result = target.checkParameters(0, 0); // super: false, derived: false;
        assertFalse(result);
    }

    @Test
    public void testCheckParameters4() {
        final boolean result = target.checkParameters(-1, -1); // super: false, derived: true;
        assertFalse(result);
    }

}
