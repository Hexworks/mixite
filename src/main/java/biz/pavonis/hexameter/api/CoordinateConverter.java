package biz.pavonis.hexameter.api;

/**
 * Utility class for converting coordinated from the offset system to
 * the axial one (the library uses the latter).
 */
public class CoordinateConverter {

	private CoordinateConverter() {
	}

	public static final int convertOffsetCoordinatesToAxialX(int x, int y, HexagonOrientation orientation) {
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? x : x - (int) Math.floor(y / 2);
	}

	public static final int convertOffsetCoordinatesToAxialZ(int x, int y, HexagonOrientation orientation) {
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? y - (int) Math.floor(x / 2) : y;
	}

	/**
	 * Creates a key based on a grid coordinate to be used in lookups.
	 * 
	 * @param gridX
	 * @param gridZ
	 * @return key based on coordinate
	 */
	public static final String createKeyFromCoordinate(int gridX, int gridZ) {
		return gridX + "," + gridZ;
	}

	/**
	 * Creates an {@link AxialCoordinate} based on a key.
	 * 
	 * @param key
	 * @return {@link AxialCoordinate}
	 */
	public static final AxialCoordinate createCoordinateFromKey(String key) {
		return new AxialCoordinate(Integer.valueOf(key.split(",")[0]), Integer.valueOf(key.split(",")[1]));
	}
}