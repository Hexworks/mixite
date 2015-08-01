package biz.pavonis.hexameter.internal.impl;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashSet;
import java.util.Set;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridCalculator;

public final class HexagonalGridCalculatorImpl implements
        HexagonalGridCalculator {

    private final HexagonalGrid hexagonalGrid;

    public HexagonalGridCalculatorImpl(HexagonalGrid hexagonalGrid) {
        this.hexagonalGrid = hexagonalGrid;
    }

    public int calculateDistanceBetween(Hexagon hex0, Hexagon hex1) {
        double absX = abs(hex0.getGridX() - hex1.getGridX());
        double absY = abs(hex0.getGridY() - hex1.getGridY());
        double absZ = abs(hex0.getGridZ() - hex1.getGridZ());
        return (int) max(max(absX, absY), absZ);
    }

    	public Set<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance) {
		Set<Hexagon> ret = new HashSet<Hexagon>();
		for (int x = -distance; x <= distance; x++) {
			for (int y = max(-distance, -x - distance); y <= min(distance, -x + distance); y++) {
				int z = -x - y;
				int tmpX = hexagon.getGridX() + x;
				int tmpZ = hexagon.getGridZ() + z;
				if (hexagonalGrid.containsCoordinate(tmpX, tmpZ)) {
					Hexagon hex = hexagonalGrid.getByGridCoordinate(tmpX, tmpZ);
					ret.add(hex);
				}
			}
		}
		return ret;
	}
}
