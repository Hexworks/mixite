package org.codetome.hexameter.core.api;

import static java.lang.Math.sqrt;

/**
 * Represents a point. Please note that this represents a point in
 * 2d space not an abstract concept of a coordinate.
 */
public final class Point {

    private final double coordinateX;
    private final double coordinateY;

    private Point(final double coordinateX, final double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Creates a point from coordinateX and coordinateY positions.
     *
     * @param coordinateX x
     * @param coordinateY y
     *
     * @return point
     */
    public static Point fromPosition(final double coordinateX, final double coordinateY) {
        return new Point(coordinateX, coordinateY);
    }

    /**
     * Calculates a distance between two points.
     *
     * @param point point
     *
     * @return distance
     */
    public double distanceFrom(final Point point) {
        return sqrt((this.coordinateX - point.coordinateX) * (this.coordinateX - point.coordinateX)
                + (this.coordinateY - point.coordinateY) * (this.coordinateY - point.coordinateY));
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }
}
