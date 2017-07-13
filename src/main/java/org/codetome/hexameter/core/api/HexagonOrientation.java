package org.codetome.hexameter.core.api;

/**
 * Enum representing the possible orientations of a {@link Hexagon}. The names
 * are self-explanatory.
 */
public enum HexagonOrientation {

    POINTY_TOP(0.5f), FLAT_TOP(0);

    private float coordinateOffset;

    HexagonOrientation(final float coordinateOffset) {
        this.coordinateOffset = coordinateOffset;
    }

    /**
     * This is because the flat/pointy shape of a hexagon.
     * It needs to be offset for pointy when calculating
     * the coordinates of its points.
     *
     * @return offset
     */
    public final float getCoordinateOffset() {
        return coordinateOffset;
    }

}
