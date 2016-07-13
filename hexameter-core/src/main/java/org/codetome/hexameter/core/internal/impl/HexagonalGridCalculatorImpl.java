package org.codetome.hexameter.core.internal.impl;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridCalculator;
import org.codetome.hexameter.core.api.RotationDirection;
import org.codetome.hexameter.core.backport.Optional;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

    @Override
    public Set<Hexagon> calculateRingFrom(Hexagon centerHexagon, int radius) {
        final Set<Hexagon> result = new HashSet<>();
        final int neighborIndex = 0;
        Hexagon currentHexagon = centerHexagon;
        for (int i = 0; i < radius; i++) {
            final Optional<Hexagon> neighbor = hexagonalGrid.getNeighborByIndex(currentHexagon, neighborIndex);
            if (neighbor.isPresent()) {
                currentHexagon = neighbor.get();
            } else {
                return result;
            }
        }
        return result;
    }

    @Override
    public List<Hexagon> drawLine(Hexagon from, Hexagon to) {
        int distance = calculateDistanceBetween(from, to);
        List<Hexagon> results = new LinkedList<>();
        for (int i = 0; i <= distance; i++) {
            CubeCoordinate h = cubeLinearInterpolate(from.getCubeCoordinate(), to.getCubeCoordinate(),
                    (1.0 / distance) * i);
            results.add(hexagonalGrid.getByCubeCoordinate(h).get());
        }
        return results;
    }

    private CubeCoordinate cubeLinearInterpolate(CubeCoordinate from, CubeCoordinate to, double t) {
        return roundToCubeCoordinate(linearInterpolate(from.getGridX(), to.getGridX(), t),
                linearInterpolate(from.getGridY(), to.getGridY(), t),
                linearInterpolate(from.getGridZ(), to.getGridZ(), t));
    }

    private double linearInterpolate(int from, int to, double t) {
        return from + (to - from) * t;
    }

    private CubeCoordinate roundToCubeCoordinate(double x, double y, double z) {
        int rx = (int) Math.round(x);
        int ry = (int) Math.round(y);
        int rz = (int) Math.round(z);

        double x_diff = Math.abs(rx - x);
        double y_diff = Math.abs(ry - y);
        double z_diff = Math.abs(rz - z);

        if (x_diff > y_diff && x_diff > z_diff) {
            rx = -ry - rz;
        } else if (y_diff > z_diff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }
        return CubeCoordinate.fromCoordinates(rx, rz);
    }
}
