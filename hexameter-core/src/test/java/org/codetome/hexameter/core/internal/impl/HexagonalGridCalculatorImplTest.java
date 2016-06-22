package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.core.api.CubeCoordinate.fromCoordinates;

public class HexagonalGridCalculatorImplTest {

    private HexagonalGrid grid;
    private HexagonalGridCalculator target;

    @Before
    public void setUp() throws Exception {
        final HexagonalGridBuilder builder = new HexagonalGridBuilder()
                .setGridHeight(10).setGridWidth(10).setRadius(10);
        grid = builder.build();
        target = builder.buildCalculatorFor(grid);
    }

    @Test
    public void shouldProperlyCalculateDistanceBetweenTwoHexes() {
        final Hexagon hex0 = grid.getByCubeCoordinate(fromCoordinates(1, 1)).get();
        final Hexagon hex1 = grid.getByCubeCoordinate(fromCoordinates(4, 5)).get();
        assertEquals(7, target.calculateDistanceBetween(hex0, hex1));
    }

    @Test
    public void shouldProperlyCalculateMovementRangeFromHexWith1() {
        final Hexagon hex = grid.getByCubeCoordinate(fromCoordinates(3, 7)).get();
        final Set<Hexagon> expected = new HashSet<>();
        expected.add(hex);
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 7)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 7)).get());
        final Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldProperlyCalculateMovementRangeFromHexWith2() {
        final Hexagon hex = grid.getByCubeCoordinate(fromCoordinates(3, 7)).get();
        final Set<Hexagon> expected = new HashSet<>();
        expected.add(hex);
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 7)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 7)).get());

        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 5)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 5)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 5)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 6)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 7)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 7)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 8)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 9)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 9)).get());
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 9)).get());

        final Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 2);
        assertEquals(expected, actual);
    }

}
