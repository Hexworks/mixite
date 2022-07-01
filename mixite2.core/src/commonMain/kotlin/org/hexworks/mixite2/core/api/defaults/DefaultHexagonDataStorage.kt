package org.hexworks.mixite2.core.api.defaults

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.contract.HexagonDataStorage
import org.hexworks.mixite2.core.api.contract.SatelliteData

class DefaultHexagonDataStorage<T : SatelliteData> : HexagonDataStorage<T> {

    private val storage = LinkedHashMap<org.hexworks.mixite2.core.api.CubeCoordinate, Maybe<T>>()

    override val coordinates: Iterable<org.hexworks.mixite2.core.api.CubeCoordinate>
        get() = storage.keys

    override fun addCoordinate(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate) {
        storage[cubeCoordinate] = Maybe.empty()
    }

    override fun addCoordinate(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate, satelliteData: T): Boolean {
        val previous = storage.put(cubeCoordinate, Maybe.of(satelliteData))
        return previous != null
    }

    override fun getSatelliteDataBy(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate): Maybe<T> {
        return if (storage.containsKey(cubeCoordinate)) storage[cubeCoordinate]!! else Maybe.empty()
    }

    override fun containsCoordinate(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate): Boolean {
        return storage.containsKey(cubeCoordinate)
    }

    override fun hasDataFor(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate): Boolean {
        return storage.containsKey(cubeCoordinate) && storage[cubeCoordinate]!!.isPresent
    }

    override fun clearDataFor(cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate): Boolean {
        var result = false
        if (hasDataFor(cubeCoordinate)) {
            result = true
        }
        storage[cubeCoordinate] = Maybe.empty()
        return result
    }
}
