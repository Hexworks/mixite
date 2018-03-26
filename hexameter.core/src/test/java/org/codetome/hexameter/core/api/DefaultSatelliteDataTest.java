package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultSatelliteDataTest {

    private static final double EXPECTED_MOVEMENT_COST = 5.1;
    private static final boolean EXPECTED_IS_PASSABLE = true;
    private static final boolean EXPECTED_IS_BLOCKS_VIEW = true;

    private DefaultSatelliteData target;


    @Before
    public void setUp() throws Exception {
        target = new DefaultSatelliteData() {
        };
    }

    @Test
    public void shouldProperlySetAndGetIsPassable() {
        target.setPassable(EXPECTED_IS_PASSABLE);
        Assert.assertEquals(target.isPassable(), EXPECTED_IS_PASSABLE);
    }

    @Test
    public void shouldProperlySetAndGetIsBlocksView() {
        target.setOpaque(EXPECTED_IS_BLOCKS_VIEW);
        Assert.assertEquals(target.isOpaque(), EXPECTED_IS_BLOCKS_VIEW);
    }

    @Test
    public void shouldProperlySetAndGetMovementCost() {
        target.setMovementCost(EXPECTED_MOVEMENT_COST);
        Assert.assertEquals(EXPECTED_MOVEMENT_COST, target.getMovementCost(), 0);
    }
}
