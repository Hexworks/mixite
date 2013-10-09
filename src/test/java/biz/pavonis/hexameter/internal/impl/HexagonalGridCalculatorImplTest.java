package biz.pavonis.hexameter.internal.impl;

import static junit.framework.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridBuilder;
import biz.pavonis.hexameter.api.HexagonalGridCalculator;

public class HexagonalGridCalculatorImplTest {

	private HexagonalGrid grid;
	private HexagonalGridCalculator target;

	@Before
	public void setUp() throws Exception {
		HexagonalGridBuilder builder = new HexagonalGridBuilder().setGridHeight(10).setGridWidth(10).setRadius(10);
		grid = builder.build();
		target = builder.buildCalculatorFor(grid);
	}

	@Test
	public void testCalculateDistanceBetween() {
		Hexagon hex0 = grid.getByGridCoordinate(1, 1);
		Hexagon hex1 = grid.getByGridCoordinate(4, 5);
		assertEquals(7, target.calculateDistanceBetween(hex0, hex1));
	}

	@Test
	public void testCalculateMovementRangeFromWithDistanceOf1() {
		Hexagon hex = grid.getByGridCoordinate(3, 7);
		Set<Hexagon> expected = new HashSet<Hexagon>();
		expected.add(grid.getByGridCoordinate(3, 6));
		expected.add(grid.getByGridCoordinate(4, 6));
		expected.add(grid.getByGridCoordinate(4, 7));
		expected.add(grid.getByGridCoordinate(3, 8));
		expected.add(grid.getByGridCoordinate(2, 8));
		expected.add(grid.getByGridCoordinate(2, 7));
		Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 1);
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateMovementRangeFromWithDistanceOf2() {
		Hexagon hex = grid.getByGridCoordinate(3, 7);
		Set<Hexagon> expected = new HashSet<Hexagon>();
		expected.add(grid.getByGridCoordinate(3, 6));
		expected.add(grid.getByGridCoordinate(4, 6));
		expected.add(grid.getByGridCoordinate(4, 7));
		expected.add(grid.getByGridCoordinate(3, 8));
		expected.add(grid.getByGridCoordinate(2, 8));
		expected.add(grid.getByGridCoordinate(2, 7));

		expected.add(grid.getByGridCoordinate(3, 5));
		expected.add(grid.getByGridCoordinate(4, 5));
		expected.add(grid.getByGridCoordinate(5, 5));
		expected.add(grid.getByGridCoordinate(2, 6));
		expected.add(grid.getByGridCoordinate(5, 6));
		expected.add(grid.getByGridCoordinate(1, 7));
		expected.add(grid.getByGridCoordinate(5, 7));
		expected.add(grid.getByGridCoordinate(1, 8));
		expected.add(grid.getByGridCoordinate(4, 8));
		expected.add(grid.getByGridCoordinate(1, 9));
		expected.add(grid.getByGridCoordinate(3, 9));
		expected.add(grid.getByGridCoordinate(2, 9));

		Set<Hexagon> actual = target.calculateMovementRangeFrom(hex, 2);
		assertEquals(expected, actual);
	}

}
