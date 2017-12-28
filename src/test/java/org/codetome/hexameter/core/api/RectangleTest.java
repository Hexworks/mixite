package org.codetome.hexameter.core.api;

import org.junit.Test;
/**
 * Created by Mikhail Kolpakov on 28.12.17.
 */
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RectangleTest {
    @Test
    public void containsPointTest() {
        Rectangle rectangle = new Rectangle(0, 0, 100, 100);
        assertTrue(rectangle.contains(50, 50));
        assertFalse(rectangle.contains(150, 110));
    }

    @Test
    public void containsRectTest() {
        Rectangle rectangle = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle(0, 0, 10, 10);
        Rectangle rectangle3 = new Rectangle(11, 11, 50, 50);
        assertFalse(rectangle.contains(rectangle));
        assertFalse(rectangle.contains(rectangle2));
        assertTrue(rectangle.contains(rectangle3));
    }

    @Test
    public void overlapsTest() {
        Rectangle rectangle = new Rectangle(30, 30, 100, 100);
        Rectangle rectangle2 = new Rectangle(0, 0, 50, 50);
        Rectangle rectangle3 = new Rectangle(31, 31, 50, 50);
        Rectangle rectangle4 = new Rectangle(0, 0, 10, 10);
        assertTrue(rectangle.overlaps(rectangle2));
        assertTrue(rectangle.overlaps(rectangle3));
        assertFalse(rectangle.overlaps(rectangle4));
    }


    @Test
    public void setCenterTest() {
        Rectangle rectangle = new Rectangle(0, 0, 1, 1);
        rectangle.setCenter(1, 1);
        assertEquals(rectangle.getX(), 0.5f);
        assertEquals(rectangle.getY(), 0.5f);
    }

    @Test
    public void getAspectRatioTest() {
        Rectangle rectangle = new Rectangle(0, 0, 1920, 1080);
        assertEquals(rectangle.getAspectRatio(), 16f / 9);
    }
}
