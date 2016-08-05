package org.codetome.hexameter.core.api;


/**
 * Utility class for converting coordinated from the offset system to
 * the cube one (the library uses the latter).
 */
public final class CoordinateConverter {

    public CoordinateConverter() {
        throw new UnsupportedOperationException("This utility class is not meant to be instantiated.");
    }

    /**
     * Calculates the cube X coordinate based on an offset coordinate pair.
     *
     * @param offsetX offset x
     * @param offsetY offset y
     * @param orientation orientation
     *
     * @return cube x
     */
    public static int convertOffsetCoordinatesToCubeX(final int offsetX, final int offsetY, final HexagonOrientation orientation) {
        return HexagonOrientation.FLAT_TOP.equals(orientation) ? offsetX : offsetX - offsetY / 2;
    }

    /**
     * Calculates the cube Z coordinate based on an offset coordinate pair.
     *
     * @param offsetX offset x
     * @param offsetY offset y
     * @param orientation orientation
     *
     * @return cube z
     */
    public static int convertOffsetCoordinatesToCubeZ(final int offsetX, final int offsetY, final HexagonOrientation orientation) {
        return HexagonOrientation.FLAT_TOP.equals(orientation) ? offsetY - offsetX / 2 : offsetY;
    }

}
