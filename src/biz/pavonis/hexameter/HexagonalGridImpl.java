package biz.pavonis.hexameter;

import java.util.ArrayList;
import java.util.List;

import biz.pavonis.hexameter.HexagonImpl.Builder;

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
				int leftOffset = calculateLeftOffset();
				Builder hexBuilder = new HexagonImpl.Builder().orientation(builder.getOrientation())
						.radius(builder.getRadius()).gridX(x + calculateCurrentOffset(leftOffset, y)).gridY(y)
						.leftOffset(leftOffset);
				innerList.add(hexBuilder.build());
			}
			hexagons.add(innerList);
		}
	}

	private int calculateCurrentOffset(int leftOffset, int y) {
		return leftOffset - (int) (Math.floor(y / 2));
	}

	private int calculateLeftOffset() {
		return gridHeight / 2;
	}

	@Override
	public List<List<Hexagon>> getHexagons() {
		// TODO: this one must be immutable
		return hexagons;
	}

	@Override
	public Hexagon getByGridCoordinate(int x, int y) {
		return hexagons.get(y).get(x - calculateCurrentOffset(calculateLeftOffset(), y));
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
