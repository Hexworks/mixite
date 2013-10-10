package biz.pavonis.hexameter.api;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import biz.pavonis.hexameter.api.exception.HexagonalGridCreationException;
import biz.pavonis.hexameter.internal.SharedHexagonData;
import biz.pavonis.hexameter.internal.impl.HexagonalGridCalculatorImpl;
import biz.pavonis.hexameter.internal.impl.HexagonalGridImpl;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.GridLayoutStrategy;

/**
 * <p>Builder for a {@link HexagonalGrid}.
 * Can be used to build a {@link HexagonalGrid}.
 * Mandatory parameters are:</p>
 * <ul>
 * <li>width of the grid</li>
 * <li>height of the grid</li>
 * <li>radius of a {@link Hexagon}</li>
 * </ul>
 * You only have to care about the setters in this class the getters are
 * for internal purposes.
 */
public final class HexagonalGridBuilder {
	private int gridWidth;
	private int gridHeight;
	private double radius;
	private Map<String, Hexagon> customStorage;
	private List<AxialCoordinate> customCoordinates = new ArrayList<AxialCoordinate>();
	private HexagonOrientation orientation = HexagonOrientation.POINTY_TOP;
	private HexagonalGridLayout gridLayout = HexagonalGridLayout.RECTANGULAR;

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
	 * Sets the {@link HexagonalGridLayout} which will be used when creating the {@link HexagonalGrid}.
	 * If it is not set <pre>RECTANGULAR</pre> will be assumed.
	 * 
	 * @param gridLayout
	 * @return this {@link HexagonalGridBuilder}.
	 */
	public HexagonalGridBuilder setGridLayout(HexagonalGridLayout gridLayout) {
		this.gridLayout = gridLayout;
		return this;
	}

	/**
	 * Adds a custom coordinate to the {@link HexagonalGrid} which will be produced.
	 * 
	 * @param axialCoordinate
	 * @return this {@link HexagonalGridBuilder}.
	 */
	public HexagonalGridBuilder addCustomAxialCoordinate(AxialCoordinate axialCoordinate) {
		customCoordinates.add(axialCoordinate);
		return this;
	}

	/**
	 * Sets a custom storage object to the {@link HexagonalGrid}. It will be used
	 * instead of the internal storage. You can pass any custom storage object
	 * as long as it implements the {@link Map} interface. Refer to the swt example
	 * project for examples. <strong>Please note</strong> that if you supply a Map
	 * which is not empty the {@link HexagonalGrid} will overwrite its contents.
	 * Methods you must implement:
	 * <ul>
	 * <li> {@link Map#containsKey(Object)}</li>
	 * <li> {@link Map#get(Object)}</li>
	 * <li> {@link Map#putAll(Map)}</li>
	 * <li> {@link Map#put(Object, Object)}</li>
	 * <li> {@link Map#remove(Object)}</li>
	 * <li> {@link Map#keySet()}</li>
	 * </ul>
	 * Others are not necessary but highly recommended. Refer to the javadoc of {@link AbstractMap} if you need help.
	 * 
	 * @param customStorage
	 * @return this {@link HexagonalGridBuilder}.
	 */
	public HexagonalGridBuilder setCustomStorage(Map<String, Hexagon> customStorage) {
		this.customStorage = customStorage;
		return this;
	}

	/**
	 * Builds a {@link HexagonalGrid} using the parameters supplied.
	 * Throws {@link HexagonalGridCreationException} if not all mandatory parameters
	 * are filled and/or they are not valid. In both cases you will be supplied with
	 * a {@link HexagonalGridCreationException} detailing the cause of failure.
	 * 
	 * @return {@link HexagonalGrid}
	 */
	public HexagonalGrid build() {
		checkParameters();
		return new HexagonalGridImpl(this);
	}

	/**
	 * Creates a {@link HexagonalGridCalculator} for your {@link HexagonalGrid}.
	 * 
	 * @param hexagonalGrid
	 * @return calculator
	 */
	public HexagonalGridCalculator buildCalculatorFor(HexagonalGrid hexagonalGrid) {
		return new HexagonalGridCalculatorImpl(hexagonalGrid);
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

	public double getRadius() {
		return radius;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public HexagonOrientation getOrientation() {
		return orientation;
	}

	public GridLayoutStrategy getGridLayoutStrategy() {
		return gridLayout.getGridLayoutStrategy();
	}

	public List<AxialCoordinate> getCustomCoordinates() {
		return customCoordinates;
	}

	public Map<String, Hexagon> getCustomStorage() {
		return customStorage;
	}

	public SharedHexagonData getSharedHexagonData() {
		if (orientation == null || radius == 0) {
			throw new IllegalStateException("orientation or radius is not yet initialized");
		}
		return new SharedHexagonData(orientation, radius);
	}

}