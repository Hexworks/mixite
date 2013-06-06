package biz.pavonis.hexameter;

import biz.pavonis.hexameter.exception.HexagonalGridCreationException;

/**
 * <p>Builder for a {@link HexagonalGrid}.
 * Can be used to build a {@link HexagonalGrid}.
 * Mandatory parameters are:</p>
 * <ul>
 * <li>width of the grid</li>
 * <li>height of the grid</li>
 * <li>radius of a {@link Hexagon}</li>
 * </ul>
 */
public class HexagonalGridBuilder {
	private int gridWidth;
	private int gridHeight;
	private HexagonOrientation orientation = HexagonOrientation.POINTY;
	private double radius;

	/**
	 * Mandatory parameter. Sets the number of {@link Hexagon}s in the horizontal direction.
	 * 
	 * @param gridWidth
	 * @return this {@link HexagonalGridBuilder}
	 */
	public HexagonalGridBuilder setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
		return this;
	}

	/**
	 * Mandatory parameter. Sets the number of {@link Hexagon}s in the vertical direction.
	 * 
	 * @param gridHeight
	 * @return this {@link HexagonalGridBuilder}
	 */
	public HexagonalGridBuilder setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
		return this;
	}

	/**
	 * Sets the {@link HexagonOrientation} used in the resulting {@link HexagonalGrid}.
	 * If it is not set HexagonOrientation.POINTY will be used.
	 * 
	 * @param orientation
	 * @return this {@link HexagonalGridBuilder}
	 */
	public HexagonalGridBuilder setOrientation(HexagonOrientation orientation) {
		this.orientation = orientation;
		return this;
	}

	/**
	 * Sets the radius of the {@link Hexagon}s contained in the resulting {@link HexagonalGrid}.
	 * 
	 * @param radius in pixels
	 * @return this {@link HexagonalGridBuilder}
	 */
	public HexagonalGridBuilder setRadius(double radius) {
		this.radius = radius;
		return this;
	}

	/**
	 * Builds a {@link HexagonalGrid} using the parameters supplied.
	 * Throws {@link HexagonalGridCreationException} if not all mandatory parameters
	 * are filled.
	 * 
	 * @return {@link HexagonalGrid}
	 */
	public HexagonalGrid build() {
		checkParameters();
		return new HexagonalGridImpl(this);
	}

	private void checkParameters() {
		if (gridWidth <= 0) {
			throw new HexagonalGridCreationException("Grid width must be greater than 0.");
		} else if (gridHeight <= 0) {
			throw new HexagonalGridCreationException("Grid height must be greater than 0.");
		} else if (orientation == null) {
			throw new HexagonalGridCreationException("Orientation must not be null.");
		} else if (radius <= 0) {
			throw new HexagonalGridCreationException("Radius must be greater than 0.");
		}
	}

	double getRadius() {
		return radius;
	}

	int getGridWidth() {
		return gridWidth;
	}

	int getGridHeight() {
		return gridHeight;
	}

	HexagonOrientation getOrientation() {
		return orientation;
	}

}