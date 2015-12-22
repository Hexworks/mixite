package org.codetome.hexameter.api;

import static java.lang.Math.sqrt;
import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.api.Point.fromPosition;

import org.junit.Test;

public class PointTest {

	@Test
	public void shouldProperlyCreatePointWhenConstructorIsCalled() {
		final double x = 0;
		final double y = 1;
		final Point p = fromPosition(x, y);
		assertEquals(x, p.getX());
		assertEquals(y, p.getY());
	}

	@Test
	public void shouldProperlyCalculateDistanceBetweenTwoPoints() {
		final int y2 = 5;
		final int y1 = 4;
		final int x2 = 9;
		final int x1 = 6;
		final double expectedDistance = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		final double actualDistance = fromPosition(x1, y1).distanceFrom(fromPosition(x2, y2));
		assertEquals(expectedDistance, actualDistance);
	}
}
