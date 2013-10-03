package biz.pavonis.hexameter.api;

/**
 * Represents an axial coorinate pair.
 */
public final class AxialCoordinate {

	private final int gridX;
	private final int gridZ;

	public AxialCoordinate(int gridX, int gridZ) {
		super();
		this.gridX = gridX;
		this.gridZ = gridZ;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridZ() {
		return gridZ;
	}

}
