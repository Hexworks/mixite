package org.hexworks.mixite2.core.api.defaults

import org.hexworks.mixite2.core.api.CubeCoordinate
import kotlin.test.*

class DefaultHexagonDataStorageTest {

    lateinit var target: DefaultHexagonDataStorage<DefaultSatelliteData>
    lateinit var defaultSatelliteDataStub: DefaultSatelliteData

    @BeforeTest
    fun setUp() {
        target = DefaultHexagonDataStorage()
        defaultSatelliteDataStub = DefaultSatelliteData()
    }

    @Test
    fun shouldAddCoordinateWhenAddCoordinateIsCalledWithCubeCoordinate() {
        target.addCoordinate(TEST_CUBE_COORDINATE)
        assertTrue {
            target.containsCoordinate(TEST_CUBE_COORDINATE)
        }
    }

    @Test
    fun shouldAddCoordinateAndDataWhenCalledWithBoth() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)
        assertTrue {
            target.containsCoordinate(TEST_CUBE_COORDINATE)
        }
        assertTrue {
            target.hasDataFor(TEST_CUBE_COORDINATE)
        }
        assertEquals(expected = defaultSatelliteDataStub, actual = target.getSatelliteDataBy(TEST_CUBE_COORDINATE).get())
    }

    @Test
    fun shouldProperlySignalModificationWhenReplacesPreviousValue() {
        target.addCoordinate(TEST_CUBE_COORDINATE)

        assertTrue {
            target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)
        }
    }

    @Test
    fun shouldProperlyReturnEmptyOptionalWhenTryingToGetNonPresentData() {
        target.addCoordinate(TEST_CUBE_COORDINATE)
        val result = target.getSatelliteDataBy(TEST_CUBE_COORDINATE)

        assertFalse {
            result.isPresent
        }
    }

    @Test
    fun shouldProperlyReturnEmptyOptionalWhenTryingToGetDataWithNonPresentKey() {
        val result = target.getSatelliteDataBy(TEST_CUBE_COORDINATE)

        assertFalse {
            result.isPresent
        }
    }

    @Test
    fun shouldProperlyReturnFalseWhenContainsCoordinateIsCalledWithNonPresentCoordinate() {
        assertFalse {
            target.containsCoordinate(TEST_CUBE_COORDINATE)
        }
    }

    @Test
    fun shouldProperlyReturnTrueWhenContainsCoordinateIsCalledWithPresentCoordinate() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)

        assertTrue {
            target.containsCoordinate(TEST_CUBE_COORDINATE)
        }
    }

    @Test
    fun shouldProperlyReturnFalseWhenHasDataForIsCalledAndThereIsNoData() {
        assertFalse {
            target.hasDataFor(TEST_CUBE_COORDINATE)
        }
    }

    @Test
    fun shouldProperlyReturnTrueWhenHasDataForIsCalledAndThereIsData() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)

        assertTrue {
            target.hasDataFor(TEST_CUBE_COORDINATE)
        }
    }

    @Test
    fun shouldProperlyReturnCoordinatesWhenThereAreNonePresent() {
        assertFalse {
            target.coordinates.iterator().hasNext()
        }
    }

    @Test
    fun shouldProperlyReturnCoordinatesWhenThereAreSeveralPresent() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)
        target.addCoordinate(ANOTHER_TEST_CUBE_COORDINATE, defaultSatelliteDataStub)

        val iter = target.coordinates.iterator()
        var count = 0
        while (iter.hasNext()) {
            iter.next()
            count++
        }
        assertEquals(expected = 2, actual = count)
    }

    @Test
    fun shouldProperlyClearDataForWhenCalledAndDataIsPresent() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataStub)

        assertTrue {
            target.hasDataFor(TEST_CUBE_COORDINATE)
        }

        target.clearDataFor(TEST_CUBE_COORDINATE)

        assertFalse {
            target.hasDataFor(TEST_CUBE_COORDINATE)
        }
    }

    companion object {

        private val TEST_CUBE_COORDINATE = org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(1, 1)
        private val ANOTHER_TEST_CUBE_COORDINATE = org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(2, 1)
    }

}
