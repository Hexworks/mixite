package org.codetome.hexameter.core.internal;

import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.junit.After;
import org.junit.Test;

import static java.lang.Math.sqrt;
import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class GridDataTest {

    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final HexagonalGridLayout GRID_LAYOUT = RECTANGULAR;
    private static final double RADIUS = 30;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 30;

    private GridData target;

    @After
    public void tearDown() {
        target = null;
    }

    @Test
    public void shouldProperlyReturnRadiusWhenGetRadiusIsCalled() {
        target = new GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT);
        assertEquals(RADIUS, target.getRadius());
    }

    @Test
    public void shouldProperlyCalculateWidthWithPointyHexagonsWhenGetWidthIsCalled() {
        target = createWithPointy();
        final double expectedWidth = sqrt(3) * RADIUS;
        final double actualWidth = target.getHexagonWidth();
        assertEquals(expectedWidth, actualWidth);
    }

    private GridData createWithPointy() {
        return new GridData(POINTY_TOP, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT);
    }

    @Test
    public void shouldProperlyCalculateWidthWithFlatHexagonsWhenGetWidthIsCalled() {
        target = createWithFlat();
        final double expectedWidth = RADIUS * 3 / 2;
        final double actualWidth = target.getHexagonWidth();
        assertEquals(expectedWidth, actualWidth);
    }

    private GridData createWithFlat() {
        return new GridData(FLAT_TOP, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT);
    }

    @Test
    public void shouldProperlyCalculateHeightWithPointyHexagonsWhenGetHeightIsCalled() {
        target = createWithPointy();
        final double expectedHeight = RADIUS * 3 / 2;
        final double actualHeight = target.getHexagonHeight();
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    public void shouldProperlyCalculateHeightWithFlatHexagonsWhenGetHeightIsCalled() {
        target = createWithFlat();
        final double expectedHeight = sqrt(3) * RADIUS;
        final double actualHeight = target.getHexagonHeight();
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    public void shouldReturnProperOrientationWhenGetOrientationIsCalled() {
        target = new GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT);
        assertEquals(ORIENTATION, target.getOrientation());
    }

    @Test
    public void shouldReturnProperCoordinateOffsetWhengetCoordinateOffsetIsCalled() {
        target = new GridData(ORIENTATION, GRID_LAYOUT, RADIUS, GRID_WIDTH, GRID_HEIGHT);
        assertEquals(ORIENTATION.getCoordinateOffset(), target.getOrientation().getCoordinateOffset());
    }
}
