package org.codetome.hexameter.core.internal;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;

import static java.lang.Math.sqrt;

/**
 * Immutable class which holds the shared data between the {@link Hexagon}s of a {@link HexagonalGrid}.
 * This is just for optimization of memory usage.
 */
public final class SharedHexagonData {

    private final HexagonOrientation orientation;
    private final double radius;
    private final double height;
    private final double width;

    /**
     * Creates a new SharedHexagonData based on <code>orientation</code> and <code>radius</code>.
     */
    public SharedHexagonData(final HexagonOrientation orientation, final double radius) {
        this.orientation = orientation;
        this.radius = radius;
        this.height = HexagonOrientation.FLAT_TOP.equals(orientation) ? calculateHeight(radius) : calculateWidth(radius);
        this.width = HexagonOrientation.FLAT_TOP.equals(orientation) ? calculateWidth(radius) : calculateHeight(radius);
    }

    private double calculateHeight(final double radius) {
        return sqrt(3) * radius;
    }

    private double calculateWidth(final double radius) {
        return radius * 3 / 2;
    }

    public HexagonOrientation getOrientation() {
        return orientation;
    }

    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
