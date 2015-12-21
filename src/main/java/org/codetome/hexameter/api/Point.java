package org.codetome.hexameter.api;

import static java.lang.Math.sqrt;

/**
 * Represents a point.
 */
public final class Point {
	public final double x;
	public final double y;

	public Point(final double x, final double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates a distance between two points.
	 *
	 * @param p0
	 * @param p1
	 * @return distance
	 */
	public final static double distance(final Point p0, final Point p1) {
		return sqrt((p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y));
	}
}
