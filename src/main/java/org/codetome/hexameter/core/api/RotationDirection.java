package org.codetome.hexameter.core.api;

/**
 * Represents a right or left angle (60°) of a Hexagon rotation.
 * See: http://www.redblobgames.com/grids/hexagons/#rotation
 */
public enum RotationDirection {

    RIGHT(new RotationCalculator() {
        @Override
        public CubeCoordinate calculate(final CubeCoordinate coord) {
            return CubeCoordinate.fromCoordinates(-coord.getGridZ(), -coord.getGridY());
        }
    }),
    LEFT(new RotationCalculator() {
        @Override
        public CubeCoordinate calculate(final CubeCoordinate coord) {
            return CubeCoordinate.fromCoordinates(-coord.getGridY(), -coord.getGridX());
        }
    });

    private RotationCalculator rotationCalculator;

    RotationDirection(final RotationCalculator rotationCalculator) {
        this.rotationCalculator = rotationCalculator;
    }

    /**
     * Calculates a rotation (right or left) of <code>coord</code>.
     *
     * @param coord coordinate to rotate
     *
     * @return rotated coordinate
     */
    public CubeCoordinate calculateRotation(final CubeCoordinate coord) {
        return rotationCalculator.calculate(coord);
    }

    interface RotationCalculator {
        CubeCoordinate calculate(CubeCoordinate coord);
    }
}
