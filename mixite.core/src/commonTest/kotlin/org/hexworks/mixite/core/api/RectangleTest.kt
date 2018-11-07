package org.hexworks.mixite.core.api

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class RectangleTest {

    @Test
    fun containsPointTest() {
        val rectangle = Rectangle(0.0, 0.0, 100.0, 100.0)
        assertTrue(rectangle.contains(50.0, 50.0))
        assertFalse(rectangle.contains(150.0, 110.0))
    }

    @Test
    fun containsRectTest() {
        val rectangle = Rectangle(0.0, 0.0, 100.0, 100.0)
        val rectangle2 = Rectangle(0.0, 0.0, 10.0, 10.0)
        val rectangle3 = Rectangle(11.0, 11.0, 50.0, 50.0)
        assertFalse(rectangle.contains(rectangle))
        assertFalse(rectangle.contains(rectangle2))
        assertTrue(rectangle.contains(rectangle3))
    }

    @Test
    fun overlapsTest() {
        val rectangle = Rectangle(30.0, 30.0, 100.0, 100.0)
        val rectangle2 = Rectangle(0.0, 0.0, 50.0, 50.0)
        val rectangle3 = Rectangle(31.0, 31.0, 50.0, 50.0)
        val rectangle4 = Rectangle(0.0, 0.0, 10.0, 10.0)
        assertTrue(rectangle.overlaps(rectangle2))
        assertTrue(rectangle.overlaps(rectangle3))
        assertFalse(rectangle.overlaps(rectangle4))
    }


    @Test
    fun setCenterTest() {
        val rectangle = Rectangle(0.0, 0.0, 1.0, 1.0)
        rectangle.withCenter(1.0, 1.0)
        assertEquals(expected = 0.5, actual = rectangle.x)
        assertEquals(expected = 0.5, actual = rectangle.y)
    }

    @Test
    fun getAspectRatioTest() {
        val rectangle = Rectangle(0.0, 0.0, 1920.0, 1080.0)
        assertEquals(rectangle.aspectRatio, 16.0 / 9.0)
    }
}
