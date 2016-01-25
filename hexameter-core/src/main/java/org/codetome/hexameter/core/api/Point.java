package org.codetome.hexameter.core.api;

import static java.lang.Math.sqrt;

/**
 * Represents a point. Please note that this represents a point in
 * 2d space not an abstract concept of a coordinate.
 */
public final class Point {
    private final double x;
    private final double y;

    private Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a point from x and y positions.
     */
    public static Point fromPosition(final double x, final double y) {
        return new Point(x, y);
    }

    /**
     * Calculates a distance between two points.
     *
     * @return distance
     */
    public final double distanceFrom(final Point point) {
        return sqrt((this.x - point.x) * (this.x - point.x) + (this.y - point.y) * (this.y - point.y));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
