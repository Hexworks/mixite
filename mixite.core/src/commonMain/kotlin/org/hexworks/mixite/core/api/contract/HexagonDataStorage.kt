package org.hexworks.mixite.core.api.contract

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite.core.api.CubeCoordinate

/**
 * This interface represents all storage operations which are needed for a working
 * [HexagonalGrid].
 * @param <T> the type of the stored [SatelliteData] implementation
 */
interface HexagonDataStorage<T : SatelliteData> {

    /**
     * Returns all coordinates which are stored in this object.
     */
    val coordinates: Iterable<CubeCoordinate>

    /**
     * Adds a [CubeCoordinate] for this grid without any [SatelliteData].
     * Does not overwrite the coordinate if it is already present.
     */
    fun addCoordinate(cubeCoordinate: CubeCoordinate)

    /**
     * Adds a [CubeCoordinate] for this grid with [SatelliteData].
     * Overwrites previous [SatelliteData] if it was present.
     * @return true if overwrote data false otherwise.
     */
    fun addCoordinate(cubeCoordinate: CubeCoordinate, satelliteData: T): Boolean

    /**
     * Gets the [SatelliteData] stored on a [CubeCoordinate] if present.
     * Also returns empty [Maybe] when `cubeCoordinate` is not present.
     * @return optional [SatelliteData].
     */
    fun getSatelliteDataBy(cubeCoordinate: CubeCoordinate): Maybe<T>

    /**
     * Tells whether there is a [Hexagon] on the given [CubeCoordinate] or not.
     * @return true if present false if not
     */
    fun containsCoordinate(cubeCoordinate: CubeCoordinate): Boolean

    /**
     * Tells whether there is [SatelliteData] stored for a [CubeCoordinate] or not.
     * Also returns false if `cubeCoordinate` is not present in the storage.
     */
    fun hasDataFor(cubeCoordinate: CubeCoordinate): Boolean

    /**
     * Clears the [SatelliteData] for the given [CubeCoordinate].
     * @return true if the storage was changed false otherwise.
     */
    fun clearDataFor(cubeCoordinate: CubeCoordinate): Boolean

}
