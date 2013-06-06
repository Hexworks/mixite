package biz.pavonis.hexameter;

/**
 * Enum representing the possible orientations of a {@link Hexagon}. The names
 * are self-explanatory.
 */
public enum HexagonOrientation {

	POINTY(0.5f), FLAT(0);

	private float coordinateOffset;

	private HexagonOrientation(float coordinateOffset) {
		this.coordinateOffset = coordinateOffset;
	}

	float getCoordinateOffset() {
		return coordinateOffset;
	}

}
