package biz.pavonis.hexameter.internal.impl;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import biz.pavonis.hexameter.api.Hexagon;
import biz.pavonis.hexameter.api.HexagonalGrid;
import biz.pavonis.hexameter.api.HexagonalGridCalculator;

public class HexagonalGridCalculatorImpl implements HexagonalGridCalculator {

	private final HexagonalGrid hexagonalGrid;

	public HexagonalGridCalculatorImpl(HexagonalGrid hexagonalGrid) {
		this.hexagonalGrid = hexagonalGrid;
	}

	public final int calculateDistanceBetween(Hexagon hex0, Hexagon hex1) {
		double absX = abs(hex0.getGridX() - hex1.getGridX());
		double absY = abs(hex0.getGridY() - hex1.getGridY());
		double absZ = abs(hex0.getGridZ() - hex1.getGridZ());
		return (int) max(max(absX, absY), absZ);
	}

	public final Set<Hexagon> calculateMovementRangeFrom(Hexagon hexagon, int distance) {
		// TODO: this is not optimal. Rewrite needed.
		Set<Hexagon> ret = new HashSet<Hexagon>();
		Set<Hexagon> currNeighbors = Collections.singleton(hexagon);
		for (int i = 0; i < distance; i++) {
			Set<Hexagon> newNeighbors = new HashSet<Hexagon>();
			for (Hexagon neighbor : currNeighbors) {
				newNeighbors.addAll(hexagonalGrid.getNeighborsOf(neighbor));
			}
			newNeighbors.removeAll(currNeighbors);
			for (Hexagon neighbor : newNeighbors) {
				ret.add(neighbor);
			}
			currNeighbors = newNeighbors;
		}
		ret.remove(hexagon);
		return ret;
	}

}
