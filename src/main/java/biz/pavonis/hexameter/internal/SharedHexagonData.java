package biz.pavonis.hexameter.internal;

import static java.lang.Math.sqrt;
import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonOrientation;
import biz.pavonis.hexameter.api.HexagonalGrid;

/**
 * Immutable class which holds the shared data between the {@link Hexagon}s of a {@link HexagonalGrid}.
 * This is just for optimization of memory usage.
 */
public final class SharedHexagonData {

	private final HexagonOrientation orientation;
	private final double radius;
	private final double height;
	private final double width;

	public SharedHexagonData(HexagonOrientation orientation, double radius) {
		this.orientation = orientation;
		this.radius = radius;
		this.height = HexagonOrientation.FLAT_TOP.equals(orientation) ? calculateHeight(radius) : calculateWidth(radius);
		this.width = HexagonOrientation.FLAT_TOP.equals(orientation) ? calculateWidth(radius) : calculateHeight(radius);
	}

	private final double calculateHeight(double radius) {
		return sqrt(3) * radius;
	}

	private final double calculateWidth(double radius) {
		return radius * 3 / 2;
	}

	public final HexagonOrientation getOrientation() {
		return orientation;
	}

	public final double getRadius() {
		return radius;
	}

	public final double getHeight() {
		return height;
	}

	public final double getWidth() {
		return width;
	}
}
