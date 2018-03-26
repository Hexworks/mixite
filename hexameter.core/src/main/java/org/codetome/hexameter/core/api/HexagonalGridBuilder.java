package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.api.defaults.DefaultHexagonDataStorage;
import org.codetome.hexameter.core.api.exception.HexagonalGridCreationException;
import org.codetome.hexameter.core.internal.GridData;
import org.codetome.hexameter.core.internal.impl.HexagonalGridCalculatorImpl;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;
import org.codetome.hexameter.core.internal.impl.layoutstrategy.GridLayoutStrategy;

/**
 * <p>Builder for a {@link HexagonalGrid}.
 * Can be used to build a {@link HexagonalGrid}.
 * Mandatory parameters are:</p>
 * <ul>
 * <li>width of the grid</li>
 * <li>height of the grid</li>
 * <li>radius of a {@link Hexagon}</li>
 * </ul>
 * Defaults for orientation and grid layout are POINTY_TOP and RECTANGULAR.
 */
public final class HexagonalGridBuilder<T extends SatelliteData> {
    private int gridWidth;
    private int gridHeight;
    private double radius;
    private HexagonDataStorage hexagonDataStorage = new DefaultHexagonDataStorage();
    private HexagonOrientation orientation = HexagonOrientation.POINTY_TOP;
    private HexagonalGridLayout gridLayout = HexagonalGridLayout.RECTANGULAR;

    /**
     * Builds a {@link HexagonalGrid} using the parameters supplied.
     * Throws {@link HexagonalGridCreationException} if not all mandatory parameters
     * are filled and/or they are not valid. In both cases you will be supplied with
     * a {@link HexagonalGridCreationException} detailing the cause of failure.
     *
     * @return {@link HexagonalGrid}
     */
    public HexagonalGrid<T> build() {
        checkParameters();
        return new HexagonalGridImpl<>(this);
    }

    private void checkParameters() {
        if (orientation == null) {
            throw new HexagonalGridCreationException("Orientation must be set.");
        }
        if (radius <= 0) {
            throw new HexagonalGridCreationException("Radius must be greater than 0.");
        }
        if (gridLayout == null) {
            throw new HexagonalGridCreationException("Grid layout must be set.");
        }
        if (!gridLayout.checkParameters(gridHeight, gridWidth)) {
            throw new HexagonalGridCreationException("Width: " + gridWidth + " and height: " + gridHeight + " is not valid for: " + gridLayout.name() + " layout.");
        }
    }

    /**
     * Creates a {@link HexagonalGridCalculator} for your {@link HexagonalGrid}.
     *
     * @param hexagonalGrid grid
     *
     * @return calculator
     */
    public HexagonalGridCalculator buildCalculatorFor(final HexagonalGrid hexagonalGrid) {
        return new HexagonalGridCalculatorImpl(hexagonalGrid);
    }

    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the {@link Hexagon}s contained in the resulting {@link HexagonalGrid}.
     *
     * @param radius in pixels
     *
     * @return this {@link HexagonalGridBuilder}
     */
    public HexagonalGridBuilder setRadius(final double radius) {
        this.radius = radius;
        return this;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Mandatory parameter. Sets the number of {@link Hexagon}s in the horizontal direction.
     *
     * @param gridWidth grid width
     *
     * @return this {@link HexagonalGridBuilder}
     */
    public HexagonalGridBuilder setGridWidth(final int gridWidth) {
        this.gridWidth = gridWidth;
        return this;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Mandatory parameter. Sets the number of {@link Hexagon}s in the vertical direction.
     *
     * @param gridHeight grid height
     *
     * @return this {@link HexagonalGridBuilder}
     */
    public HexagonalGridBuilder setGridHeight(final int gridHeight) {
        this.gridHeight = gridHeight;
        return this;
    }

    public HexagonOrientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the {@link HexagonOrientation} used in the resulting {@link HexagonalGrid}.
     * If it is not set HexagonOrientation.POINTY will be used.
     *
     * @param orientation orientation
     *
     * @return this {@link HexagonalGridBuilder}
     */
    public HexagonalGridBuilder setOrientation(final HexagonOrientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public GridLayoutStrategy getGridLayoutStrategy() {
        return gridLayout.getGridLayoutStrategy();
    }

    public HexagonDataStorage<T> getHexagonDataStorage() {
        return hexagonDataStorage;
    }

    /**
     * Returns the GridData.
     *
     * @return grid data
     */
    public GridData getGridData() {
        if (orientation == null || gridLayout == null || radius == 0 || gridWidth == 0 || gridHeight == 0) {
            throw new IllegalStateException("Not all necessary fields are initialized!");
        }
        return new GridData(orientation, gridLayout, radius, gridWidth, gridHeight);
    }

    /**
     * Sets the {@link HexagonalGridLayout} which will be used when creating the {@link HexagonalGrid}.
     * If it is not set <pre>RECTANGULAR</pre> will be assumed.
     *
     * @param gridLayout layout
     *
     * @return this {@link HexagonalGridBuilder}.
     */
    public HexagonalGridBuilder setGridLayout(final HexagonalGridLayout gridLayout) {
        this.gridLayout = gridLayout;
        return this;
    }
}
