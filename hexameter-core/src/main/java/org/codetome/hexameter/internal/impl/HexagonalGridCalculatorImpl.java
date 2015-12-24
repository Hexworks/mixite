package org.codetome.hexameter.internal.impl;

import org.codetome.hexameter.api.AxialCoordinate;
import org.codetome.hexameter.api.Hexagon;
import org.codetome.hexameter.api.HexagonalGrid;
import org.codetome.hexameter.api.HexagonalGridCalculator;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.*;
import static org.codetome.hexameter.api.AxialCoordinate.fromCoordinates;

public final class HexagonalGridCalculatorImpl implements HexagonalGridCalculator {

    private final HexagonalGrid hexagonalGrid;

    public HexagonalGridCalculatorImpl(final HexagonalGrid hexagonalGrid) {
        this.hexagonalGrid = hexagonalGrid;
    }

    @Override
    public int calculateDistanceBetween(final Hexagon hex0, final Hexagon hex1) {
        final double absX = abs(hex0.getGridX() - hex1.getGridX());
        final double absY = abs(hex0.getGridY() - hex1.getGridY());
        final double absZ = abs(hex0.getGridZ() - hex1.getGridZ());
        return (int) max(max(absX, absY), absZ);
    }

    @Override
    public Set<Hexagon> calculateMovementRangeFrom(final Hexagon hexagon, final int distance) {
        final Set<Hexagon> ret = new HashSet<>();
        for (int x = -distance; x <= distance; x++) {
            for (int y = max(-distance, -x - distance); y <= min(distance, -x + distance); y++) {
                final int z = -x - y;
                final int tmpX = hexagon.getGridX() + x;
                final int tmpZ = hexagon.getGridZ() + z;
                final AxialCoordinate tempCoordinate = fromCoordinates(tmpX, tmpZ);
                if (hexagonalGrid.containsAxialCoordinate(tempCoordinate)) {
                    final Hexagon hex = hexagonalGrid.getByAxialCoordinate(tempCoordinate).get();
                    ret.add(hex);
                }
            }
        }
        return ret;
    }
}
