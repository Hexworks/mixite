package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.CubeCoordinate.Companion.fromAxialKey
import org.hexworks.mixite.core.api.CubeCoordinate.Companion.fromCoordinates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CoordinateConverterTest {

    @Test
    fun shouldThrowExceptionWhenInstantiated() {
        assertFailsWith<UnsupportedOperationException> {
            CoordinateConverter()
        }
    }

    @Test
    fun shouldConvertOffsetCoordinatesToAxialXWithPointy() {
        val result = CoordinateConverter.convertOffsetCoordinatesToCubeX(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP)
        assertEquals(EXPECTED_AXIAL_X_WITH_POINTY, result)
    }

    @Test
    fun shouldConvertOffsetCoordinatesToAxialXWithFlat() {
        val result = CoordinateConverter.convertOffsetCoordinatesToCubeX(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP)
        assertEquals(EXPECTED_AXIAL_X_WITH_FLAT, result)
    }

    @Test
    fun shouldConvertOffsetCoordinatesToAxialZWithPointy() {
        val result = CoordinateConverter.convertOffsetCoordinatesToCubeZ(TEST_X, TEST_Y, HexagonOrientation.POINTY_TOP)
        assertEquals(EXPECTED_AXIAL_Z_WITH_POINTY, result)
    }

    @Test
    fun shouldConvertOffsetCoordinatesToAxialZWithFlat() {
        val result = CoordinateConverter.convertOffsetCoordinatesToCubeZ(TEST_X, TEST_Y, HexagonOrientation.FLAT_TOP)
        assertEquals(EXPECTED_AXIAL_Z_WITH_FLAT, result)
    }

    @Test
    fun shouldCreateKeyFromCoordinate() {
        assertEquals(TEST_KEY, fromCoordinates(TEST_GRID_X, TEST_GRID_Z).toAxialKey())
    }

    @Test
    fun shouldCreateCoordinateFromKey() {
        val c = fromAxialKey(TEST_KEY)
        assertEquals(TEST_GRID_X, c.gridX)
        assertEquals(TEST_GRID_Z, c.gridZ)
    }

    companion object {

        private const val TEST_X = 3
        private const val TEST_Y = 4
        private const val TEST_KEY = "7,8"
        private const val TEST_GRID_X = 7
        private const val TEST_GRID_Z = 8

        private const val EXPECTED_AXIAL_X_WITH_POINTY = 1
        private const val EXPECTED_AXIAL_X_WITH_FLAT = 3
        private const val EXPECTED_AXIAL_Z_WITH_POINTY = 4
        private const val EXPECTED_AXIAL_Z_WITH_FLAT = 3
    }
}
