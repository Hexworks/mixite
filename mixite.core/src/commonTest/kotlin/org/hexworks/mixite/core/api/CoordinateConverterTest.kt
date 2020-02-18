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
    fun shouldConvertCubeCoordinatesToOffsetRowWithFlat() {
        for((i, cube) in testCubes.withIndex()) {
            val result = CoordinateConverter.convertCubeCoordinateToOffsetRow(cube, HexagonOrientation.FLAT_TOP)
            assertEquals(expectedOffsetRowWithFlat[i], result, "(" + cube.gridX + "," + cube.gridZ + ") to " + result)
        }
    }

    @Test
    fun shouldConvertCubeCoordinatesToOffsetColWithFlat() {
        for((i, cube) in testCubes.withIndex()) {
            val result = CoordinateConverter.convertCubeCoordinateToOffsetColumn(cube, HexagonOrientation.FLAT_TOP)
            assertEquals(expectedOffsetColWithFlat[i], result, "(" + cube.gridX + "," + cube.gridZ + ") to " + result)
        }
    }

    @Test
    fun shouldConvertCubeCoordinatesToOffsetRowWithPointy() {
        for((i, cube) in testCubes.withIndex()) {
            val result = CoordinateConverter.convertCubeCoordinateToOffsetRow(cube, HexagonOrientation.POINTY_TOP)
            assertEquals(expectedOffsetRowWithPointy[i], result, "(" + cube.gridX + "," + cube.gridZ + ") to " + result)
        }
    }

    @Test
    fun shouldConvertCubeCoordinatesToOffsetColWithPointy() {
        for((i, cube) in testCubes.withIndex()) {
            val result = CoordinateConverter.convertCubeCoordinateToOffsetColumn(cube, HexagonOrientation.POINTY_TOP)
            assertEquals(expectedOffsetColWithPointy[i], result, "(" + cube.gridX + "," + cube.gridZ + ") to " + result)
        }
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

        private val testCubes = arrayOf(
            CubeCoordinate.fromCoordinates(-1, -2),
            CubeCoordinate.fromCoordinates( 2, -3),
            CubeCoordinate.fromCoordinates( 7,  8)
        )

        private val expectedOffsetColWithFlat   = intArrayOf(-1, 2, 7)
        private val expectedOffsetRowWithFlat   = intArrayOf(-3,-2,11)
        private val expectedOffsetColWithPointy = intArrayOf(-2, 0,11)
        private val expectedOffsetRowWithPointy = intArrayOf(-2,-3, 8)
    }
}
