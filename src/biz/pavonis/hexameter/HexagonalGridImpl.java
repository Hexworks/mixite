package biz.pavonis.hexameter;

import java.util.List;

import biz.pavonis.hexameter.exception.HexagonNotFoundException;

class HexagonalGridImpl implements HexagonalGrid {

	private List<List<Hexagon>> hexagons;
	private GridLayoutStrategy layoutStrategy;

	HexagonalGridImpl(HexagonalGridBuilder builder) {
		layoutStrategy = builder.getGridLayout().getGridLayoutStrategy();
		hexagons = layoutStrategy.createHexagons(builder);
	}

	private void checkCoordinate(int x, int y) {
		int xIndex = x - layoutStrategy.calculateCurrentOffset(y);
		try {
			hexagons.get(y).get(xIndex);
		} catch (IndexOutOfBoundsException e) {
			throw new HexagonNotFoundException("Hexagon (" + x + "," + y + ") is off the grid.", e);
		}
	}

	@Override
	public List<List<Hexagon>> getHexagons() {
		return hexagons;
	}

	@Override
	public Hexagon getByGridCoordinate(int x, int y) {
		checkCoordinate(x, y);
		return hexagons.get(y).get(x - layoutStrategy.calculateCurrentOffset(y));
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
