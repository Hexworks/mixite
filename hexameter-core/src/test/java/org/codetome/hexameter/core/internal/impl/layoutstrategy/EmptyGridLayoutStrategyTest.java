package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayouStrategyTestUtil.fetchDefaultBuilder;

public class EmptyGridLayoutStrategyTest {

    private EmptyGridLayoutStrategy target;
    private HexagonalGridBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = fetchDefaultBuilder();
        target = new EmptyGridLayoutStrategy();
    }

    @Test
    public void shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        assertTrue(target.fetchGridCoordinates(builder).isEmpty());
    }

    @Test
    public void testCheckParameters0() {
        assertTrue(target.checkParameters(0, 0));
    }

    @Test
    public void testCheckParameters1() {
        assertTrue(target.checkParameters(1, 0));
    }

    @Test
    public void testCheckParameters2() {
        assertTrue(target.checkParameters(0, 1));
    }

}
