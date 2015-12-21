package org.codetome.hexameter.api;

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
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? x : x - (int) Math.floor(y / 2);
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
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? y - (int) Math.floor(x / 2) : y;
	}

	/**
	 * Creates a key based on a grid coordinate to be used in lookups.
	 *
	 * @param gridX
	 * @param gridZ
	 * @return key based on coordinate
	 */
	public static String createKeyFromCoordinate(final int gridX, final int gridZ) {
		return gridX + "," + gridZ;
	}

	/**
	 * Creates an {@link AxialCoordinate} based on a key.
	 *
	 * @param key
	 * @return {@link AxialCoordinate}
	 */
	public static AxialCoordinate createCoordinateFromKey(final String key) {
		return new AxialCoordinate(Integer.valueOf(key.split(",")[0]), Integer.valueOf(key.split(",")[1]));
	}
}