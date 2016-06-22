package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.CubeCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;

import java.util.Collection;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

public class TrapezoidGridLayoutStrategyTest {

    private HexagonalGridBuilder builder;
    private TrapezoidGridLayoutStrategy target;

    @Before
    public void setUp() throws Exception {
        builder = GridLayouStrategyTestUtil.fetchDefaultBuilder();
        target = new TrapezoidGridLayoutStrategy();
    }

    @Test
    public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        testCoordinates(target.fetchGridCoordinates(builder).toList().toBlocking().single());
    }

    private void testCoordinates(final Collection<CubeCoordinate> coords) {

        assertTrue(coords.contains(fromCoordinates(0, 0)));
        assertTrue(coords.contains(fromCoordinates(1, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 0)));
        assertTrue(coords.contains(fromCoordinates(2, 1)));
        assertTrue(coords.contains(fromCoordinates(2, 2)));
        assertTrue(coords.contains(fromCoordinates(1, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 2)));
        assertTrue(coords.contains(fromCoordinates(0, 1)));

        assertTrue(!coords.contains(fromCoordinates(-1, 0)));
        assertTrue(!coords.contains(fromCoordinates(0, -1)));
        assertTrue(!coords.contains(fromCoordinates(1, -1)));
        assertTrue(!coords.contains(fromCoordinates(2, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, -1)));
        assertTrue(!coords.contains(fromCoordinates(3, 0)));
        assertTrue(!coords.contains(fromCoordinates(3, 1)));
        assertTrue(!coords.contains(fromCoordinates(3, 2)));
        assertTrue(!coords.contains(fromCoordinates(2, 3)));
        assertTrue(!coords.contains(fromCoordinates(1, 3)));
        assertTrue(!coords.contains(fromCoordinates(0, 3)));
        assertTrue(!coords.contains(fromCoordinates(-1, 2)));
        assertTrue(!coords.contains(fromCoordinates(-1, 1)));
    }

    @Test
    public void shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(FLAT_TOP);
        testCoordinates(target.fetchGridCoordinates(builder).toList().toBlocking().single());
    }

    @Test
    public void shouldReturnTrueWhenCheckParametersIsCalled() {
        assertTrue(target.checkParameters(2, 2));
    }

}
