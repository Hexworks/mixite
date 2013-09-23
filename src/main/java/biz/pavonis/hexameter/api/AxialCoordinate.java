package biz.pavonis.hexameter.api;

/**
 * Represents an axial coorinate pair.
 */
public class AxialCoordinate {

	private int gridX;
	private int gridZ;

	public AxialCoordinate(int gridX, int gridZ) {
		super();
		this.gridX = gridX;
		this.gridZ = gridZ;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridZ() {
		return gridZ;
	}

	public void setGridZ(int gridZ) {
		this.gridZ = gridZ;
	}

}
