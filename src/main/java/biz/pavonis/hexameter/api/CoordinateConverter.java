package biz.pavonis.hexameter.api;

/**
 * Utility class for converting coordinated from the offset system to
 * the axial one (the library uses the latter).
 */
public final class CoordinateConverter {

	private CoordinateConverter() {
	}

	/**
	 * Calculates the axial X coordinate based on an offset coordinate pair.
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 * @return
	 */
	public static int convertOffsetCoordinatesToAxialX(int x, int y, HexagonOrientation orientation) {
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
	public static int convertOffsetCoordinatesToAxialZ(int x, int y, HexagonOrientation orientation) {
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? y - (int) Math.floor(x / 2) : y;
	}

	/**
	 * Creates a key based on a grid coordinate to be used in lookups.
	 * 
	 * @param gridX
	 * @param gridZ
	 * @return key based on coordinate
	 */
	public static String createKeyFromCoordinate(int gridX, int gridZ) {
		return gridX + "," + gridZ;
	}

	/**
	 * Creates an {@link AxialCoordinate} based on a key.
	 * 
	 * @param key
	 * @return {@link AxialCoordinate}
	 */
	public static AxialCoordinate createCoordinateFromKey(String key) {
		return new AxialCoordinate(Integer.valueOf(key.split(",")[0]), Integer.valueOf(key.split(",")[1]));
	}
}