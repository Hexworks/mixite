package org.codetome.hexameter.core.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultSatelliteDataTest {

    private static final double EXPECTED_MOVEMENT_COST = 5.1;
    private static final boolean EXPECTED_IS_PASSABLE = true;
    private static final boolean EXPECTED_IS_BLOCKS_VIEW = true;
    private static final String EXPECTED_CUSTOM_DATA = "EXPECTED_CUSTOM_DATA";
    private static final String EXPECTED_CUSTOM_DATA_KEY = "EXPECTED_CUSTOM_DATA_KEY";

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
    public void shouldProperlyGetAndSetCustomData() {
        target.addCustomData(EXPECTED_CUSTOM_DATA_KEY, EXPECTED_CUSTOM_DATA);
        final String actual = target.<String>getCustomData(EXPECTED_CUSTOM_DATA_KEY).get();
        assertThat(actual).isEqualTo(EXPECTED_CUSTOM_DATA);
    }

    @Test
    public void shouldProperlySetAndGetMovementCost() {
        target.setMovementCost(EXPECTED_MOVEMENT_COST);
        Assert.assertEquals(EXPECTED_MOVEMENT_COST, target.getMovementCost(), 0);
    }
}
