package org.codetome.hexameter.core.api;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.core.api.CubeCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.CubeCoordinate.fromAxialKey;

public class CubeCoordinateTest {

    private static final int TEST_GRID_X = 4;
    private static final int TEST_GRID_Z = 5;
    private CubeCoordinate target;

    @Before
    public void setUp() {
        target = fromCoordinates(TEST_GRID_X, TEST_GRID_Z);
    }

    @Test
    public void shouldReturnProperCoordinateWhenGetGridXIsCalled() {
        assertEquals(TEST_GRID_X, target.getGridX());
    }

    @Test
    public void shouldBeEqualToItself() {
        Assert.assertEquals(target, target);
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Assert.assertFalse(target.equals(null));
    }

    @Test
    public void shouldReturnProperCoordinateWhenGetGridZIsCalled() {
        assertEquals(TEST_GRID_Z, target.getGridZ());
    }

    @Test
    public void shouldReturnProperKeyWhenToKeyIsCalled() {
        assertEquals(TEST_GRID_X + "," + TEST_GRID_Z, target.toAxialKey());
    }

    @Test
    public void shouldCreateProperAxialCoordinateWhenFromKeyIsCalled() {
        final CubeCoordinate result = fromAxialKey(TEST_GRID_X + "," + TEST_GRID_Z);
        assertEquals(target.getGridX(), result.getGridX());
        assertEquals(target.getGridZ(), result.getGridZ());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateCoordinateFromMalformedKey() {
        CubeCoordinate.fromAxialKey(null);
    }

}
