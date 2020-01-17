package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.HexagonOrientation.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HexagonOrientationTest {

    @Test
    fun shouldProperlyCalculateFlatCoordinateOffset() {
        assertEquals(expected = 0.0f, actual = FLAT_TOP.coordinateOffset)
    }

    @Test
    fun shouldProperlyCalculatePointyCoordinateOffset() {
        assertEquals(expected = 0.5f, actual = POINTY_TOP.coordinateOffset)
    }
}
