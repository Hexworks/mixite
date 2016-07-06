package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridCalculator;
import org.codetome.hexameter.core.api.RotationDirection;
import org.codetome.hexameter.core.backport.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.codetome.hexameter.core.api.CubeCoordinate.fromCoordinates;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HexagonalGridCalculatorImplTest {

    private HexagonalGrid grid;
    private HexagonalGridCalculator target;

    @Mock
    private Hexagon originalHex;
    @Mock
    private Hexagon targetHex;
    private RotationDirection rotationDirection;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
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

    @Test
    public void shouldProperlyCalculateRotationWhenGivenAValidGrid() {
        configureMockitoForRotation();

        final Optional<Hexagon> resultOpt = target.rotateHexagon(originalHex, targetHex, RotationDirection.RIGHT);

        Hexagon result = resultOpt.get();

        assertThat(result.getGridX()).isEqualTo(3);
        assertThat(result.getGridY()).isEqualTo(-4);
        assertThat(result.getGridZ()).isEqualTo(1);
    }

    private void configureMockitoForRotation() {
        when(originalHex.getGridX()).thenReturn(3);
        when(originalHex.getGridY()).thenReturn(-2);
        when(originalHex.getGridZ()).thenReturn(-1);

        when(targetHex.getGridX()).thenReturn(5);
        when(targetHex.getGridY()).thenReturn(-4);
        when(targetHex.getGridZ()).thenReturn(-1);
    }

}
