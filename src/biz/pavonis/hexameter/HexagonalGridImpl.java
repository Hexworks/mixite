package biz.pavonis.hexameter;

import java.util.ArrayList;
import java.util.List;

import biz.pavonis.hexameter.HexagonImpl.Builder;
import biz.pavonis.hexameter.exception.HexagonNotFoundException;

class HexagonalGridImpl implements HexagonalGrid {

	private List<List<Hexagon>> hexagons;
	private int gridWidth;
	private int gridHeight;

	HexagonalGridImpl(HexagonalGridBuilder builder) {
		gridWidth = builder.getGridWidth();
		gridHeight = builder.getGridHeight();
		hexagons = new ArrayList<>(gridHeight);
		createHexagons(builder);
	}

	private void createHexagons(HexagonalGridBuilder builder) {
		for (int y = 0; y < gridHeight; y++) {
			List<Hexagon> innerList = new ArrayList<>(gridWidth);
			for (int x = 0; x < gridWidth; x++) {
				int maxLeftOffset = calculateMaxLeftOffset();
				Builder hexBuilder = new HexagonImpl.Builder().orientation(builder.getOrientation())
						.radius(builder.getRadius()).gridX(x + calculateCurrentOffset(maxLeftOffset, y)).gridY(y)
						.maxLeftOffset(maxLeftOffset);
				innerList.add(hexBuilder.build());
			}
			hexagons.add(innerList);
		}
	}

	/**
	 * Calculates the current offset from the left. This is necessary because of the
	 * diagonal nature of the y axis.
	 * 
	 * @param maxLeftOffset
	 * @param y current y coordinate
	 * @return current offset
	 */
	private int calculateCurrentOffset(int maxLeftOffset, int y) {
		return maxLeftOffset - (int) (Math.floor(y / 2));
	}

	/**
	 * Calculates the maximum offset from the left (because of the diagonal axis).
	 * 
	 * @return offset
	 */
	private int calculateMaxLeftOffset() {
		return gridHeight / 2;
	}

	@Override
	public List<List<Hexagon>> getHexagons() {
		// TODO: this one must be immutable
		return hexagons;
	}

	@Override
	public Hexagon getByGridCoordinate(int x, int y) {
		checkCoordinate(x, y);
		return hexagons.get(y).get(x - calculateCurrentOffset(calculateMaxLeftOffset(), y));
	}

	private void checkCoordinate(int x, int y) {
		int xIndex = x - calculateCurrentOffset(calculateMaxLeftOffset(), y);
		if (x <= 0) {
			throw new HexagonNotFoundException("Couldn't make sense of x coordinate: " + x
					+ ". Coordinates must be greater than 0.");
		} else if (y < 0) {
			throw new HexagonNotFoundException("Couldn't make sense of negative y coordinate: " + y + ".");
		} else if (hexagons.size() <= y) {
			throw new HexagonNotFoundException("Y coordinate is off the grid: " + y);
		} else if (hexagons.get(y).size() <= xIndex || xIndex < 0) {
			throw new HexagonNotFoundException("X coordinate is off the grid: " + x);
		}
	}

	@Override
	public List<Hexagon> getNeighborsOf(Hexagon hexagon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calculateDistanceBetween(Hexagon hex0, Hexagon hex1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hexagon getByPixelCoordinate(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
