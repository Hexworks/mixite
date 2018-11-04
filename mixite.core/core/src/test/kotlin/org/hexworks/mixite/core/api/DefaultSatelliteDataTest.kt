package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultSatelliteDataTest {

    lateinit var target: DefaultSatelliteData


    @BeforeTest
    fun setUp() {
        target = object : DefaultSatelliteData() {

        }
    }

    @Test
    fun shouldProperlySetAndGetIsPassable() {
        target.passable = EXPECTED_IS_PASSABLE
        assertEquals(EXPECTED_IS_PASSABLE, target.passable)
    }

    @Test
    fun shouldProperlySetAndGetIsBlocksView() {
        target.opaque = EXPECTED_IS_BLOCKS_VIEW
        assertEquals(EXPECTED_IS_BLOCKS_VIEW, target.opaque)
    }

    @Test
    fun shouldProperlySetAndGetMovementCost() {
        target.movementCost = EXPECTED_MOVEMENT_COST
        assertEquals(EXPECTED_MOVEMENT_COST, target.movementCost)
    }

    companion object {

        private const val EXPECTED_MOVEMENT_COST = 5.1
        private const val EXPECTED_IS_PASSABLE = true
        private const val EXPECTED_IS_BLOCKS_VIEW = true
    }
}
