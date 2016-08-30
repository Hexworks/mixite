package org.codetome.hexameter.core.api;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a cube coorinate pair.
 * See http://www.redblobgames.com/grids/hexagons/#coordinates to learn more.
 * Note that the y coordinate is not stored in this object since it can be
 * calculated.
 */
public final class CubeCoordinate implements Serializable {

    public static final String SEP = ",";
    private static final long serialVersionUID = -6656555565645274603L;
    private final int gridX;
    private final int gridZ;

    private CubeCoordinate(final int gridX, final int gridZ) {
        this.gridX = gridX;
        this.gridZ = gridZ;
    }

    /**
     * Tries to create an {@link CubeCoordinate} from a key which has the format:
     * <code>%gridX%,%gridZ%</code>.
     *
     * @param axialKey key
     *
     * @return coord
     */
    public static CubeCoordinate fromAxialKey(final String axialKey) {
        CubeCoordinate result;
        try {
            final String[] coords = axialKey.split(SEP);
            result = fromCoordinates(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to create CubeCoordinate from key: " + axialKey, e);
        }
        return result;
    }

    /**
     * Creates an instance of {@link CubeCoordinate} from an x and a z coordinate.
     *
     * @param gridX grid x
     * @param gridZ grid z
     *
     * @return coord
     */
    public static CubeCoordinate fromCoordinates(final int gridX, final int gridZ) {
        return new CubeCoordinate(gridX, gridZ);
    }

    /**
     * Creates an axial (x, z) key which can be used in key-value storage objects based on this
     * {@link CubeCoordinate}.
     *
     * @return key
     */
    public String toAxialKey() {
        return gridX + SEP + gridZ;
    }

    public int getGridY() {
        return -(getGridX() + getGridZ());
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridX, gridZ);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CubeCoordinate that = (CubeCoordinate) object;
        return gridX == that.gridX && gridZ == that.gridZ;
    }
}
