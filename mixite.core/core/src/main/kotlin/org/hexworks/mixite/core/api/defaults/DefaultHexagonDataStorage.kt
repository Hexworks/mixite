package org.hexworks.mixite.core.api.defaults

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.contract.HexagonDataStorage
import org.hexworks.mixite.core.api.contract.SatelliteData

class DefaultHexagonDataStorage<T : SatelliteData> : HexagonDataStorage<T> {

    private val storage = LinkedHashMap<CubeCoordinate, Maybe<T>>()

    override val coordinates: Iterable<CubeCoordinate>
        get() = storage.keys

    override fun addCoordinate(cubeCoordinate: CubeCoordinate) {
        storage[cubeCoordinate] = Maybe.empty()
    }

    override fun addCoordinate(cubeCoordinate: CubeCoordinate, satelliteData: T): Boolean {
        val previous = storage.put(cubeCoordinate, Maybe.of(satelliteData))
        return previous != null
    }

    override fun getSatelliteDataBy(cubeCoordinate: CubeCoordinate): Maybe<T> {
        return if (storage.containsKey(cubeCoordinate)) storage[cubeCoordinate]!! else Maybe.empty()
    }

    override fun containsCoordinate(cubeCoordinate: CubeCoordinate): Boolean {
        return storage.containsKey(cubeCoordinate)
    }

    override fun hasDataFor(cubeCoordinate: CubeCoordinate): Boolean {
        return storage.containsKey(cubeCoordinate) && storage[cubeCoordinate]!!.isPresent
    }

    override fun clearDataFor(cubeCoordinate: CubeCoordinate): Boolean {
        var result = false
        if (hasDataFor(cubeCoordinate)) {
            result = true
        }
        storage[cubeCoordinate] = Maybe.empty()
        return result
    }
}
