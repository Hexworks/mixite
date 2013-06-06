package biz.pavonis.hexameter;

class CoordinateConverter {

	private CoordinateConverter() {
	}

	static int convertOffsetCoordinatesToAxialX(int x, int y, HexagonOrientation orientation) {
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? x : x - (int) (Math.floor(y / 2));
	}

	static int convertOffsetCoordinatesToAxialZ(int x, int y, HexagonOrientation orientation) {
		return HexagonOrientation.FLAT_TOP.equals(orientation) ? y - (int) (Math.floor(x / 2)) : y;
	}
}
