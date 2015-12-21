package org.codetome.hexameter.api;

import static org.codetome.hexameter.api.HexagonOrientation.FLAT_TOP;

/**
 * Utility class for converting coordinated from the offset system to
 * the axial one (the library uses the latter).
 */
public final class CoordinateConverter {


    public CoordinateConverter() {
        throw new UnsupportedOperationException("This utility class is not meant to be instantiated.");
    }

	/**
	 * Calculates the axial X coordinate based on an offset coordinate pair.
	 *
	 * @param x
	 * @param y
	 * @param orientation
	 * @return
	 */
	public static int convertOffsetCoordinatesToAxialX(final int x, final int y, final HexagonOrientation orientation) {
		return FLAT_TOP.equals(orientation) ? x : x - (int) Math.floor(y / 2);
	}

	/**
	 * Calculates the axial Z coordinate based on an offset coordinate pair.
	 *
	 * @param x
	 * @param y
	 * @param orientation
	 * @return
	 */
	public static int convertOffsetCoordinatesToAxialZ(final int x, final int y, final HexagonOrientation orientation) {
		return FLAT_TOP.equals(orientation) ? y - (int) Math.floor(x / 2) : y;
	}

}