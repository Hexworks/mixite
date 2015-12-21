package org.codetome.hexameter.internal.impl;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridCalculator;
import org.codetome.hexameter.api.PathfindingAlgorithm;

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
		Set<Hexagon> ret = new HashSet<> ();
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

	private class HexIntComparator implements Comparator<Hexagon>
  {
		@Override
		public int compare(Hexagon o1, Hexagon o2) {
			Integer prio1 = (Integer) o1.getSatelliteData();
			Integer prio2 = (Integer) o2.getSatelliteData();
			return prio1.compareTo(prio2);
		}
	}

	@Override
	public List<Hexagon> calculatePath(Hexagon start, Hexagon end, PathfindingAlgorithm algorithm) {
		Map<Hexagon, Hexagon> cameFrom;
		switch (algorithm) {
		case A_Star:
			cameFrom = aStar(start, end);
			break;
		case BreadthFirst:
			cameFrom = breadthFirst(start, end);
			break;
		case GreedyBreadthFirst:
			cameFrom = greedyBestFirst(start, end);
			break;
		default:
			return null;
		}

		Hexagon current = end;
		List<Hexagon> path = new LinkedList<> ();
		path.add(current);
		while (!current.equals(start)) {
			current = cameFrom.get(current);
			path.add(current);
		}
		Collections.reverse(path);
		return path;
	}

	private Map<Hexagon, Hexagon> aStar(Hexagon start, Hexagon end) {
		PriorityQueue<Hexagon> frontier = new PriorityQueue<> (11, new HexIntComparator ());
		start.setSatelliteData(0);
		frontier.add(start);
		Map<Hexagon, Hexagon> cameFrom = new HashMap<> ();
		cameFrom.put(start, null);
		Map<Hexagon, Integer> costSoFar = new HashMap<> ();
		costSoFar.put(start, 0);

		while (!frontier.isEmpty()) {
			Hexagon current = frontier.poll();
			if (current.equals(end)) {
				break;
			}
			Set<Hexagon> neigbours = calculateMovementRangeFrom(current, 1);
			for (Hexagon next : neigbours) {
				int newCost = costSoFar.get(current) + calculateDistanceBetween(current, next);
				if ((!costSoFar.containsKey(next)) || newCost < costSoFar.get(next)) {
					costSoFar.put(next, newCost);
					int prio = calculateDistanceBetween(end, next);
					next.setSatelliteData(prio);
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return cameFrom;
	}

	private Map<Hexagon, Hexagon> breadthFirst(Hexagon start, Hexagon end) {
		Queue<Hexagon> frontier = new LinkedList<> ();
		frontier.add(start);
		Map<Hexagon, Hexagon> cameFrom = new HashMap<> ();
		cameFrom.put(start, null);

		while (!frontier.isEmpty()) {
			Hexagon current = frontier.poll();
			if (current.equals(end)) {
				break;
			}
			Set<Hexagon> neigbours = calculateMovementRangeFrom(current, 1);
			for (Hexagon next : neigbours) {
				if (!cameFrom.containsKey(next)) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return cameFrom;
	}

	private Map<Hexagon, Hexagon> greedyBestFirst(Hexagon start, Hexagon end) {
		PriorityQueue<Hexagon> frontier = new PriorityQueue<> (11, new HexIntComparator ());
		start.setSatelliteData(0);
		frontier.add(start);
		Map<Hexagon, Hexagon> cameFrom = new HashMap<> ();
		cameFrom.put(start, null);

		while (!frontier.isEmpty()) {
			Hexagon current = frontier.poll();
			if (current.equals(end)) {
				break;
			}
			Set<Hexagon> neigbours = calculateMovementRangeFrom(current, 1);
			for (Hexagon next : neigbours) {
				if (!cameFrom.containsKey(next)) {
					int prio = calculateDistanceBetween(end, next);
					next.setSatelliteData(prio);
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return cameFrom;
	}
}
