package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.DefaultSatelliteData;
import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.SatelliteData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
import static org.junit.Assert.assertArrayEquals;

public class HexagonalGridImplTest {

    private static final int RADIUS = 30;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    private static final HexagonOrientation ORIENTATION = POINTY_TOP;
    private static final int GRID_X_FROM = 2;
    private static final int GRID_X_TO = 4;
    private static final int GRID_Z_FROM = 3;
    private static final int GRID_Z_TO = 5;
    private HexagonalGridImpl target;
    private HexagonalGridBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
        target = (HexagonalGridImpl) builder.build();
    }

    @Test
    public void shouldReturnHexagonsInProperIterationOrderWhenGetHexagonsIsCalled() {
        final Collection<AxialCoordinate> expectedCoordinates = builder.getGridLayoutStrategy().fetchGridCoordinates(builder);
        final Collection<AxialCoordinate> actualCoordinates = new LinkedList<>();
        target.getHexagons().forEach(hexagon -> actualCoordinates.add(hexagon.getAxialCoordinate()));
        assertArrayEquals(expectedCoordinates.toArray(), actualCoordinates.toArray());
    }

    @Test
    public void shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid() {
        final Iterable<Hexagon> hexagons = target.getHexagons();
        final Set<String> expectedCoordinates = new HashSet<>();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                final int gridX = CoordinateConverter.convertOffsetCoordinatesToAxialX(x, y, ORIENTATION);
                final int gridZ = CoordinateConverter.convertOffsetCoordinatesToAxialZ(x, y, ORIENTATION);
                expectedCoordinates.add(gridX + "," + gridZ);
            }
        }
        AtomicInteger count = new AtomicInteger();
        hexagons.forEach(hexagon -> {
            expectedCoordinates.remove(hexagon.getGridX() + "," + hexagon.getGridZ());
            count.incrementAndGet();
        });
        assertEquals(100, count.get());
        assertTrue(expectedCoordinates.isEmpty());
    }

    @Test
    public void shouldReturnProperHexagonsWhenGetHexagonsByAxialRangeIsCalled() {
        final Set<Hexagon> expected = new HashSet<>();

        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 3)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 3)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 3)).get());

        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 4)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 4)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 4)).get());

        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 5)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 5)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 5)).get());

        final Iterable<Hexagon> actual = target.getHexagonsByAxialRange(fromCoordinates(GRID_X_FROM, GRID_Z_FROM), fromCoordinates(GRID_X_TO, GRID_Z_TO));
        final AtomicInteger count = new AtomicInteger();
        actual.forEach(hex -> count.incrementAndGet());
        assertEquals(expected.size(), count.get());
        actual.forEach(hex -> expected.remove(hex));
        assertTrue(expected.isEmpty());
    }

    @Test
    public void shouldReturnProperHexagonsWhenGetHexagonsByOffsetRangeIsCalled() {
        final Set<Hexagon> expected = new HashSet<>();

        expected.add(target.getByAxialCoordinate(fromCoordinates(1, 3)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 3)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 3)).get());

        expected.add(target.getByAxialCoordinate(fromCoordinates(0, 4)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(1, 4)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 4)).get());

        expected.add(target.getByAxialCoordinate(fromCoordinates(0, 5)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(1, 5)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 5)).get());

        final Iterable<Hexagon> actual = target.getHexagonsByOffsetRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO);
        final AtomicInteger count = new AtomicInteger();
        actual.forEach(hex -> count.incrementAndGet());
        assertEquals(expected.size(), count.get());
        actual.forEach(hex -> expected.remove(hex));
        assertTrue(expected.isEmpty());
    }

    @Test
    public void shouldContainCoordinateWhenContainsCoorinateIsCalledWithProperParameters() {
        final int gridX = 2;
        final int gridZ = 3;
        assertTrue(target.containsAxialCoordinate(fromCoordinates(gridX, gridZ)));
    }

    @Test
    public void shouldReturnHexagonWhenGetByGridCoordinateIsCalledWithProperCoordinates() {
        final int gridX = 2;
        final int gridZ = 3;
        final Optional<Hexagon> hex = target.getByAxialCoordinate(fromCoordinates(gridX, gridZ));
        assertTrue(hex.isPresent());
    }

    @Test()
    public void shouldBeEmptyWhenGetByGridCoordinateIsCalledWithInvalidCoordinates() {
        final int gridX = 20;
        final int gridZ = 30;
        Optional<Hexagon> result = target.getByAxialCoordinate(fromCoordinates(gridX, gridZ));
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnHexagonWhenCalledWithProperCoordinates() {
        final double x = 310;
        final double y = 255;
        final Hexagon hex = target.getByPixelCoordinate(x, y).get();
        assertTrue(hex.getGridX() == 3);
        assertTrue(hex.getGridZ() == 5);
    }

    @Test
    public void shouldReturnHexagonWhenCalledWithProperCoordinates2() {
        final double x = 300;
        final double y = 275;
        final Hexagon hex = target.getByPixelCoordinate(x, y).get();
        assertTrue(hex.getGridX() == 3);
        assertTrue(hex.getGridZ() == 5);
    }

    @Test
    public void shouldReturnHexagonWhenCalledWithProperCoordinates3() {
        final double x = 325;
        final double y = 275;
        final Hexagon hex = target.getByPixelCoordinate(x, y).get();
        assertTrue(hex.getGridX() == 3);
        assertTrue(hex.getGridZ() == 5);
    }

    @Test
    public void shouldReturnProperNeighborsOfHexagonWhenHexIsInMiddle() {
        final Hexagon hex = target.getByAxialCoordinate(fromCoordinates(3, 7)).get();
        final Set<Hexagon> expected = new HashSet<>();
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 6)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 6)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 7)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(3, 8)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 8)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(2, 7)).get());
        final Iterable<Hexagon> actual = target.getNeighborsOf(hex);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge() {
        final Hexagon hex = target.getByAxialCoordinate(fromCoordinates(5, 9)).get();
        final Set<Hexagon> expected = new HashSet<>();
        expected.add(target.getByAxialCoordinate(fromCoordinates(5, 8)).get());
        expected.add(target.getByAxialCoordinate(fromCoordinates(4, 9)).get());
        final Iterable<Hexagon> actual = target.getNeighborsOf(hex);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldProperlyClearSatelliteDataWhenClearSatelliteDataIsCalled() {
        final Hexagon testHex = target.getByAxialCoordinate(fromCoordinates(2, 3)).get();
        final SatelliteData data = new DefaultSatelliteData() {
        };
        testHex.setSatelliteData(data);
        target.clearSatelliteData();
        assertTrue(!testHex.getSatelliteData().isPresent());
    }

    @Test
    public void shouldProperlyReturnGridLayoutWhenGetGridLayoutIsCalled() {
        Assert.assertEquals(RECTANGULAR, target.getGridLayout());
    }

    @Test
    public void shouldProperlyReturnSharedHexagonDataWhenGetSharedHexagonDataIsCalled() {
        Assert.assertEquals(builder.getSharedHexagonData().getHeight(), target.getSharedHexagonData().getHeight(), 0);
        Assert.assertEquals(builder.getSharedHexagonData().getWidth(), target.getSharedHexagonData().getWidth(), 0);
        Assert.assertEquals(builder.getSharedHexagonData().getRadius(), target.getSharedHexagonData().getRadius(), 0);
        Assert.assertEquals(builder.getSharedHexagonData().getOrientation(), target.getSharedHexagonData().getOrientation());
    }

    @Test
    public void shouldProperlyReturnGridWidthWhenGetGridWidthIsCalled() {
        Assert.assertEquals(GRID_WIDTH, target.getGridWidth());
    }

    @Test
    public void shouldProperlyReturnGridHeightWhenGetGridHeightIsCalled() {
        Assert.assertEquals(GRID_HEIGHT, target.getGridHeight());
    }
}
