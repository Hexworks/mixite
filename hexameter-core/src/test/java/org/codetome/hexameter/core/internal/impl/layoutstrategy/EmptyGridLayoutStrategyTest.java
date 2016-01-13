package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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
        final Collection<Hexagon> hexagons = target.createHexagons(builder);
        assertTrue(hexagons.isEmpty());
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
