package org.hexworks.mixite2.core.api

import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromAxialKey
import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CubeCoordinateTest {

    lateinit var target: org.hexworks.mixite2.core.api.CubeCoordinate

    @BeforeTest
    fun setUp() {
        target = fromCoordinates(TEST_GRID_X, TEST_GRID_Z)
    }

    @Test
    fun shouldReturnProperCoordinateWhenGetGridXIsCalled() {
        assertEquals(TEST_GRID_X, target.gridX)
    }

    @Test
    fun shouldBeEqualToItself() {
        assertEquals(target, target)
    }

    @Test
    fun shouldReturnProperCoordinateWhenGetGridZIsCalled() {
        assertEquals(TEST_GRID_Z, target.gridZ)
    }

    @Test
    fun shouldReturnProperKeyWhenToKeyIsCalled() {
        assertEquals(TEST_GRID_X.toString() + "," + TEST_GRID_Z, target.toAxialKey())
    }

    @Test
    fun shouldCreateProperAxialCoordinateWhenFromKeyIsCalled() {
        val result = fromAxialKey(TEST_GRID_X.toString() + "," + TEST_GRID_Z)
        assertEquals(target.gridX, result.gridX)
        assertEquals(target.gridZ, result.gridZ)
    }

    @Test
    fun shouldFailToCreateCoordinateFromMalformedKey() {
        assertFailsWith<IllegalArgumentException> {
            org.hexworks.mixite2.core.api.CubeCoordinate.fromAxialKey("asdf")
        }
    }

    companion object {

        private const val TEST_GRID_X = 4
        private const val TEST_GRID_Z = 5
    }

}
