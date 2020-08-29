package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.HexagonOrientation.POINTY_TOP
import org.hexworks.mixite.core.api.contract.HexagonDataStorage
import org.hexworks.mixite.core.api.contract.SatelliteData
import org.hexworks.mixite.core.api.defaults.DefaultHexagonDataStorage
import org.hexworks.mixite.core.internal.GridData
import org.hexworks.mixite.core.internal.impl.HexagonalGridCalculatorImpl
import org.hexworks.mixite.core.internal.impl.HexagonalGridImpl
import org.hexworks.mixite.core.internal.impl.layoutstrategy.GridLayoutStrategy
import org.hexworks.mixite.core.internal.impl.layoutstrategy.RectangularGridLayoutStrategy

/**
 *
 * Builder for a [HexagonalGrid].
 * Can be used to build a [HexagonalGrid].
 * Mandatory parameters are:
 *
 *  * width of the grid
 *  * height of the grid
 *  * radius of a [Hexagon]
 *
 * Defaults for orientation and grid layout are POINTY_TOP and RECTANGULAR.
 */
class HexagonalGridBuilder<T : SatelliteData> {
    private var gridWidth: Int = 0
    private var gridHeight: Int = 0
    private var radius: Double = 0.toDouble()
    private val hexagonDataStorage: HexagonDataStorage<T> = DefaultHexagonDataStorage()
    private var orientation = POINTY_TOP
    private var gridLayout : GridLayoutStrategy = RectangularGridLayoutStrategy()

    val gridLayoutStrategy: GridLayoutStrategy
        get() = gridLayout

    /**
     * Returns the GridData.
     *
     * @return grid data
     */
    val gridData: GridData
        get() {
            if (radius == 0.0 || gridWidth == 0 || gridHeight == 0) {
                throw IllegalStateException("Not all necessary fields are initialized!")
            }
            return GridData(orientation, gridLayout, radius, gridWidth, gridHeight)
        }

    /**
     * Builds a [HexagonalGrid] using the parameters supplied.
     * Throws [HexagonalGridCreationException] if not all mandatory parameters
     * are filled and/or they are not valid. In both cases you will be supplied with
     * a [HexagonalGridCreationException] detailing the cause of failure.
     *
     * @return [HexagonalGrid]
     */
    fun build(): HexagonalGrid<T> {
        checkParameters()
        return HexagonalGridImpl(this)
    }

    /**
     * Creates a [HexagonalGridCalculator] for your [HexagonalGrid].
     *
     * @param hexagonalGrid grid
     *
     * @return calculator
     */
    fun <T : SatelliteData> buildCalculatorFor(hexagonalGrid: HexagonalGrid<T>): HexagonalGridCalculator<T> {
        return HexagonalGridCalculatorImpl(hexagonalGrid)
    }

    fun getRadius(): Double {
        return radius
    }

    /**
     * Sets the radius of the [Hexagon]s contained in the resulting [HexagonalGrid].
     *
     * @param radius in pixels
     *
     * @return this [HexagonalGridBuilder]
     */
    fun setRadius(radius: Double): HexagonalGridBuilder<T> = also {
        this.radius = radius
    }

    fun getGridWidth(): Int {
        return gridWidth
    }

    /**
     * Mandatory parameter. Sets the number of [Hexagon]s in the horizontal direction.
     *
     * @param gridWidth grid width
     *
     * @return this [HexagonalGridBuilder]
     */
    fun setGridWidth(gridWidth: Int): HexagonalGridBuilder<T> = also {
        this.gridWidth = gridWidth
    }

    fun getGridHeight(): Int {
        return gridHeight
    }

    /**
     * Mandatory parameter. Sets the number of [Hexagon]s in the vertical direction.
     *
     * @param gridHeight grid height
     *
     * @return this [HexagonalGridBuilder]
     */
    fun setGridHeight(gridHeight: Int): HexagonalGridBuilder<T> = also {
        this.gridHeight = gridHeight
    }

    fun getOrientation(): HexagonOrientation {
        return orientation
    }

    /**
     * Sets the [HexagonOrientation] used in the resulting [HexagonalGrid].
     * If it is not set HexagonOrientation.POINTY will be used.
     *
     * @param orientation orientation
     *
     * @return this [HexagonalGridBuilder]
     */
    fun setOrientation(orientation: HexagonOrientation): HexagonalGridBuilder<T> = also {
        this.orientation = orientation
    }

    fun getHexagonDataStorage(): HexagonDataStorage<T> {
        return hexagonDataStorage
    }

    /**
     * Sets the [GridLayoutStrategy] which will be used when creating the [HexagonalGrid].
     * If it is not set <pre>RECTANGULAR</pre> will be assumed.
     *
     * @param gridLayout layout
     *
     * @return this [HexagonalGridBuilder].
     */
    fun setGridLayout(gridLayout: GridLayoutStrategy): HexagonalGridBuilder<T> = also {
        this.gridLayout = gridLayout
    }

    /**
     * Sets the [GridLayoutStrategy] which will be used when creating the [HexagonalGrid], based on an existing
     * [HexagonalGridLayout]. If it is not set <pre>RECTANGULAR</pre> will be assumed.
     *
     * @param gridLayout layout
     *
     * @return this [HexagonalGridBuilder].
     */
    fun setGridLayout(gridLayout: HexagonalGridLayout): HexagonalGridBuilder<T> = also {
        this.gridLayout = gridLayout.gridLayoutStrategy
    }

    private fun checkParameters() {
        if (radius <= 0) {
            throw IllegalStateException("Radius must be greater than 0.")
        }
        if (!gridLayout.checkParameters(gridHeight, gridWidth)) {
            throw IllegalStateException("Width: " + gridWidth + " and height: " + gridHeight + " is not valid for: " + gridLayout.getName() + " layout.")
        }
    }
}
