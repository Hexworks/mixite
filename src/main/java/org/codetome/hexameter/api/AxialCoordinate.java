package org.codetome.hexameter.api;

import static java.lang.Integer.valueOf;

/**
 * Represents an axial coorinate pair.
 * See http://www.redblobgames.com/grids/hexagons/#coordinates to learn more.
 */
public final class AxialCoordinate {

    private final int gridX;
    private final int gridZ;

    private AxialCoordinate(final int gridX, final int gridZ) {
        this.gridX = gridX;
        this.gridZ = gridZ;
    }

    /**
     * Tries to create an {@link AxialCoordinate} from a key which has the format:
     * <code>%gridX%,%gridZ%</code>.
     *
     * @param key
     *
     * @return
     */
    public static AxialCoordinate fromKey(final String key) {
        AxialCoordinate result = null;
        try {
            final String[] coords = key.split(",");
            result = fromCoordinates(valueOf(coords[0]), valueOf(coords[1]));
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to create AxialCoordinate from key: " + key, e);
        }
        return result;
    }

    /**
     * Creates an instance of {@link AxialCoordinate} from an x and a z coordinate.
     *
     * @param gridX
     * @param gridZ
     *
     * @return
     */
    public static AxialCoordinate fromCoordinates(final int gridX, final int gridZ) {
        return new AxialCoordinate(gridX, gridZ);
    }

    /**
     * Creates a key which can be used in key-value storage objects based on this
     * {@link AxialCoordinate}.
     *
     * @return
     */
    public String toKey() {
        return gridX + "," + gridZ;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

}
