package biz.pavonis.hexameter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Default implementation of the {@link Hexagon} interface.
 * This object takes up 56 bytes of memory.
 */
class HexagonImpl implements Hexagon {

	private final SharedHexagonData sharedHexagonData;
	private final double centerX;
	private final double centerY;
	private int gridX;
	private int gridZ;
	private Object satelliteData;

	HexagonImpl(SharedHexagonData sharedHexagonData, int gridX, int gridZ) {
		this.sharedHexagonData = sharedHexagonData;
		double height = sharedHexagonData.getHeight();
		double width = sharedHexagonData.getWidth();
		double radius = sharedHexagonData.getRadius();
		this.gridX = gridX;
		this.gridZ = gridZ;
		// TODO: this is ugly. I'll figure out something...
		if (HexagonOrientation.FLAT_TOP.equals(sharedHexagonData.getOrientation())) {
			centerX = gridX * width + radius;
			centerY = gridZ * height + gridX * height / 2 + height / 2;
		} else {
			centerX = gridX * width + gridZ * width / 2 + width / 2;
			centerY = gridZ * height + radius;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getSatelliteData() {
		return (T) satelliteData;
	}

	public <T> void setSatelliteData(T satelliteData) {
		this.satelliteData = satelliteData;
	}

	public Point[] getPoints() {
		Point[] points = new Point[6];
		for (int i = 0; i < 6; i++) {
			double angle = 2 * Math.PI / 6 * (i + sharedHexagonData.getOrientation().getCoordinateOffset());
			double x = centerX + sharedHexagonData.getRadius() * cos(angle);
			double y = centerY + sharedHexagonData.getRadius() * sin(angle);
			points[i] = new Point(x, y);
		}
		return points;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return -(gridX + gridZ);
	}

	public int getGridZ() {
		return gridZ;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

}
