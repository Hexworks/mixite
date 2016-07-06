package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridCalculator;
import org.codetome.hexameter.core.api.RotationDirection;
import org.codetome.hexameter.core.backport.Optional;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
                final CubeCoordinate tempCoordinate = CubeCoordinate.fromCoordinates(tmpX, tmpZ);
                if (hexagonalGrid.containsCubeCoordinate(tempCoordinate)) {
                    final Hexagon hex = hexagonalGrid.getByCubeCoordinate(tempCoordinate).get();
                    ret.add(hex);
                }
            }
        }
        return ret;
    }

    @Override
    public Optional<Hexagon> rotateHexagon(Hexagon originalHex, Hexagon targetHex, RotationDirection rotationDirection) {
        final int diffX = targetHex.getGridX() - originalHex.getGridX();
        final int diffZ = targetHex.getGridZ() - originalHex.getGridZ();
        final CubeCoordinate diffCoord = CubeCoordinate.fromCoordinates(diffX, diffZ);
        final CubeCoordinate rotatedCoord = rotationDirection.calculateRotation(diffCoord);
        final CubeCoordinate resultCoord = CubeCoordinate.fromCoordinates(
                originalHex.getGridX() + rotatedCoord.getGridX(),
                originalHex.getGridZ() + rotatedCoord.getGridZ()); // 0, x,
        return hexagonalGrid.getByCubeCoordinate(resultCoord);
    }
}
