package org.codetome.hexameter.core.api;

import lombok.Getter;

import static java.lang.Math.sqrt;

/**
 * Represents a point. Please note that this represents a point in
 * 2d space not an abstract concept of a coordinate.
 */
@Getter
public final class Point {

    private final double coordinateX;
    private final double coordinateY;

    private Point(final double coordinateX, final double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Creates a point from coordinateX and coordinateY positions.
     */
    public static Point fromPosition(final double coordinateX, final double coordinateY) {
        return new Point(coordinateX, coordinateY);
    }

    /**
     * Calculates a distance between two points.
     *
     * @return distance
     */
    public double distanceFrom(final Point point) {
        return sqrt((this.coordinateX - point.coordinateX) * (this.coordinateX - point.coordinateX)
                + (this.coordinateY - point.coordinateY) * (this.coordinateY - point.coordinateY));
    }
}
