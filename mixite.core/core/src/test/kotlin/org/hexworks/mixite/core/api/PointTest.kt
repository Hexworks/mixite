package org.hexworks.mixite.core.api

import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class PointTest {

    @Test
    fun shouldProperlyCreatePointWhenConstructorIsCalled() {
        val x = 0.0
        val y = 1.0
        val p = Point.fromPosition(x, y)
        assertEquals(expected = x, actual = p.coordinateX)
        assertEquals(expected = y, actual = p.coordinateY)
    }

    @Test
    fun shouldProperlyCalculateDistanceBetweenTwoPoints() {
        val y2 = 5.0
        val y1 = 4.0
        val x2 = 9.0
        val x1 = 6.0
        val expectedDistance = sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)))
        val actualDistance = Point.fromPosition(x1, y1).distanceFrom(Point.fromPosition(x2, y2))
        assertEquals(expectedDistance, actualDistance)
    }
}
