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

    /**
     * This test specifically targets a bug that was found in the
     * Python port.
     */
    @Test
    fun shouldProperlyCalculateLineWithFlatTop() {

        val localBuilder = HexagonalGridBuilder<DefaultSatelliteData>()
            .setGridHeight(10)
            .setGridWidth(10)
            .setGridLayout(HexagonalGridLayout.RECTANGULAR)
            .setOrientation(HexagonOrientation.FLAT_TOP)
            .setRadius(35.0)
        val localGrid = localBuilder.build()
        val localTarget = localBuilder.buildCalculatorFor(localGrid)

        val actual = localTarget.drawLine(localGrid.getByCubeCoordinate(fromCoordinates(6, 4)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(7, -2)).get())
        assertEquals(expected = listOf(
            localGrid.getByCubeCoordinate(fromCoordinates(6, 4)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(6, 3)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(6, 2)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(6, 1)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(7, 0)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(7, -1)).get(),
            localGrid.getByCubeCoordinate(fromCoordinates(7, -2)).get()),
            actual = actual)
    }

    @Test
    fun shouldCheckForVisibilityCorrectly() {

        val hexagon = grid.getByCubeCoordinate(fromCoordinates(2, 5))
        val data = DefaultSatelliteData()
        data.isOpaque = true
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
                gridX = 4,
                gridY = 0,
                gridZ = 4)

        val expected = HashSet<Hexagon<DefaultSatelliteData>>()

        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 7)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 7)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(6, 5)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(7, 4)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(7, 3)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(7, 2)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(7, 1)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(6, 1)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(5, 1)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(4, 1)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(3, 2)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 3)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 4)).get())

        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 5)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 6)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 7)).get())

        val actual = target.calculateRingFrom(targetHex, 3)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldProperlyCalculateRingWhenNearAnEdge() {
        targetHex = HexagonStub(
                gridX = 9,
                gridY = -9,
                gridZ = 0)

        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(grid.getByCubeCoordinate(fromCoordinates(8, 0)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(8, 1)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(9, 1)).get())

        val actual = target.calculateRingFrom(targetHex, 1)
        assertEquals(expected, actual)



        targetHex = HexagonStub(
                gridX = 0,
                gridY = 0,
                gridZ = 0)

        expected.clear()
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 0)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(0, 1)).get())

        val newActual = target.calculateRingFrom(targetHex, 1)
        assertEquals(expected, newActual)
    }

    @Test
    fun shouldProperlyCalculateRingWhenCenterOffEdge() {
        targetHex = HexagonStub(
                gridX = 0,
                gridY = 1,
                gridZ = -1
        )

        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(grid.getByCubeCoordinate(fromCoordinates(2, 0)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(1, 1)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(0, 2)).get())
        expected.add(grid.getByCubeCoordinate(fromCoordinates(-1, 2)).get())

        val actual =  target.calculateRingFrom(targetHex, 3)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldProperlyCalculateRingAtRadiusOne() {
        targetHex = HexagonStub(
                gridX = 4,
                gridY = 4,
                gridZ = 4)

        val expected = 6

        val actual = target.calculateRingFrom(targetHex, 1).size

        assertEquals(expected, actual)
    }

}
