package org.codetome.hexameter.core.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

/**
 * Represents an axial coorinate pair.
 * See http://www.redblobgames.com/grids/hexagons/#coordinates to learn more.
 */
@EqualsAndHashCode
@Getter
public final class AxialCoordinate implements Serializable {

    private static final long serialVersionUID = -6656555565645274603L;
    private final int gridX;
    private final int gridZ;

    private AxialCoordinate(final int gridX, final int gridZ) {
        this.gridX = gridX;
        this.gridZ = gridZ;
    }

    /**
     * Tries to create an {@link AxialCoordinate} from a key which has the format:
     * <code>%gridX%,%gridZ%</code>.
     */
    public static AxialCoordinate fromKey(final String key) {
        AxialCoordinate result;
        try {
            final String[] coords = key.split(",");
            result = fromCoordinates(parseInt(coords[0]), parseInt(coords[1]));
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to create AxialCoordinate from key: " + key, e);
        }
        return result;
    }

    /**
     * Creates an instance of {@link AxialCoordinate} from an x and a z coordinate.
     */
    public static AxialCoordinate fromCoordinates(final int gridX, final int gridZ) {
        return new AxialCoordinate(gridX, gridZ);
    }

    /**
     * Creates a key which can be used in key-value storage objects based on this
     * {@link AxialCoordinate}.
     */
    public String toKey() {
        return gridX + "," + gridZ;
    }

}
