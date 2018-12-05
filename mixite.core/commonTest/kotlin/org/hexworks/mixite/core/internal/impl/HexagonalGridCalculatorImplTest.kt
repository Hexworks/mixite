package org.hexworks.mixite.core.internal.impl

import org.hexworks.mixite.core.HexagonStub
import org.hexworks.mixite.core.api.*
import org.hexworks.mixite.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite.core.api.RotationDirection.LEFT
import org.hexworks.mixite.core.api.RotationDirection.RIGHT
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData
import kotlin.test.*

class HexagonalGridCalculatorImplTest {

    private lateinit var grid: HexagonalGrid<DefaultSatelliteData>
    private lateinit var target: HexagonalGridCalculator<DefaultSatelliteData>

    private lateinit var originalHex: Hexagon<DefaultSatelliteData>
    private lateinit var targetHex: Hexagon<DefaultSatelliteData>

    @BeforeTest
    fun setUp() {
        val builder = HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridHeight(10)
                .setGridWidth(10)
                .setRadius(10.0)
        grid = builder.build()
        target = builder.buildCalculatorFor(grid)
    }

    @Test
    fun shouldProperlyCalculateDistanceBetweenTwoHexes() {
        val hex0 = grid.getByCubeCoordinate(fromCoordinates(1, 1)).get()
        val hex1 = grid.getByCubeCoordinate(fromCoordinates(4, 5)).get()
        assertEquals(7, target.calculateDistanceBetween(hex0, hex1))
    }

    @Test
    fun shouldProperlyCalculateMovementRangeFromHexWith1() {
        val hex = grid.getByCubeCoordinate(fromCoordinates(3, 7)).get()
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(hex)
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 7)).get())
        val actual = target.calculateMovementRangeFrom(hex, 1)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldProperlyCalculateMovementRangeFromHexWith2() {
        val hex = grid.getByCubeCoordinate(fromCoordinates(3, 7)).get()
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(hex)
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 7)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 5)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 5)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 5)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 8)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 9)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 9)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 9)).get())

        val actual = target.calculateMovementRangeFrom(hex, 2)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldProperlyCalculateLineWithMultipleElements() {
        val actual = target.drawLine(grid.getByCubeCoordinate(fromCoordinates(3, 7)).get(),
                grid.getByCubeCoordinate(fromCoordinates(8, 1)).get())
        assertEquals(expected = listOf(grid.getByCubeCoordinate(fromCoordinates(3, 7)).get(),
                grid.getByCubeCoordinate(fromCoordinates(4, 6)).get(),
                grid.getByCubeCoordinate(fromCoordinates(5, 5)).get(),
                grid.getByCubeCoordinate(fromCoordinates(6, 4)).get(),
                grid.getByCubeCoordinate(fromCoordinates(6, 3)).get(),
                grid.getByCubeCoordinate(fromCoordinates(7, 2)).get(),
                grid.getByCubeCoordinate(fromCoordinates(8, 1)).get()),
                actual = actual)
    }

    @Test
    fun shouldProperlyCalculateLineWithOneElement() {
        val actual = target.drawLine(grid.getByCubeCoordinate(fromCoordinates(3, 7)).get(),
                grid.getByCubeCoordinate(fromCoordinates(3, 7)).get())
        assertTrue {
            actual.isEmpty()
        }
    }

    @Test
    fun shouldCheckForVisibilityCorrectly() {

        val hexagon = grid.getByCubeCoordinate(fromCoordinates(2, 5))
        val data = DefaultSatelliteData()
        data.opaque = true
        hexagon.get().setSatelliteData(data)

        assertFalse {
            target.isVisible(grid.getByCubeCoordinate(fromCoordinates(8, 1)).get(),
                    grid.getByCubeCoordinate(fromCoordinates(-3, 8)).get())
        }
        assertFalse {
            target.isVisible(grid.getByCubeCoordinate(fromCoordinates(4, 4)).get(),
                    grid.getByCubeCoordinate(fromCoordinates(-3, 8)).get())
        }
        assertTrue {
            target.isVisible(grid.getByCubeCoordinate(fromCoordinates(8, 3)).get(),
                    grid.getByCubeCoordinate(fromCoordinates(-3, 8)).get())
        }
        assertTrue {
            target.isVisible(grid.getByCubeCoordinate(fromCoordinates(7, 1)).get(),
                    grid.getByCubeCoordinate(fromCoordinates(-3, 8)).get())
        }
    }

    @Test
    fun shouldProperlyCalculateRotationRightWhenGivenAValidGrid() {
        originalHex = HexagonStub(
                gridX = 3,
                gridY = -2,
                gridZ = -1)
        targetHex = HexagonStub(
                gridX = 5,
                gridY = -4,
                gridZ = -1)

        val resultOpt = target.rotateHexagon(originalHex, targetHex, RIGHT)

        val result = resultOpt.get()

        assertEquals(3, result.gridX)
        assertEquals(-4, result.gridY)
        assertEquals(1, result.gridZ)
    }

    @Test
    fun shouldProperlyCalculateRotationLeftWhenGivenAValidGrid() {
        originalHex = HexagonStub(
                gridX = 5,
                gridY = -4,
                gridZ = -1)
        targetHex = HexagonStub(
                gridX = 3,
                gridY = -2,
                gridZ = -1)

        val resultOpt = target.rotateHexagon(originalHex, targetHex, LEFT)

        val result = resultOpt.get()

        assertEquals(3, result.gridX)
        assertEquals(-4, result.gridY)
        assertEquals(1, result.gridZ)
    }

    @Test
    fun shouldProperlyCalculateRingWhenGivenValidParameters() {
        targetHex = HexagonStub(
                gridX = 0,
                gridY = 0,
                gridZ = 0)

        target.calculateRingFrom(targetHex, 3)
    }


}
