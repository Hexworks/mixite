package org.codetome.hexameter.internal.impl;

import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;

import java.util.HashSet;
import java.util.Set;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridBuilder;
import org.codetome.hexameter.api.HexagonalGridCalculator;
import org.junit.Before;
import org.junit.Test;

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
        final Hexagon hex0 = grid.getByAxialCoordinate(fromCoordinates(1, 1));
        final Hexagon hex1 = grid.getByAxialCoordinate(fromCoordinates(4, 5));
        assertEquals(7, target.calculateDistanceBetween(hex0, hex1));
    }

    @Test
    public void shouldProperlyCalculateMovementRangeFromHexWith1() {
        final Hexagon hex = grid.getByAxialCoordinate(fromCoordinates(3, 7));
        final Set<Hexagon> expected = new HashSet<> ();
        expected.add(hex);
        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 7)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 7)));
        final Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldProperlyCalculateMovementRangeFromHexWith2() {
        final Hexagon hex = grid.getByAxialCoordinate(fromCoordinates(3, 7));
        final Set<Hexagon> expected = new HashSet<> ();
        expected.add(hex);
        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 7)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 7)));

        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 5)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 5)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(5, 5)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(5, 6)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(1, 7)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(5, 7)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(1, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(4, 8)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(1, 9)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(3, 9)));
        expected.add(grid.getByAxialCoordinate(fromCoordinates(2, 9)));

        final Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 2);
        assertEquals(expected, actual);
    }

}
