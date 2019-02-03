package org.hexworks.mixite.core

import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.Hexagon
import org.hexworks.mixite.core.api.Point
import org.hexworks.mixite.core.api.Rectangle
import org.hexworks.mixite.core.api.contract.SatelliteData

class HexagonStub<T: SatelliteData>(override val id: String = "",
                                    override val points: List<Point> = listOf(),
                                    override val vertices: List<Double> = listOf(),
                                    override val externalBoundingBox: Rectangle = Rectangle(0.0, 0.0, 0.0, 0.0),
                                    override val internalBoundingBox: Rectangle = Rectangle(0.0, 0.0, 0.0, 0.0),
                                    override val cubeCoordinate: CubeCoordinate = CubeCoordinate.fromCoordinates(0, 0),
                                    override val gridX: Int = 0,
                                    override val gridY: Int = 0,
                                    override val gridZ: Int = 0,
                                    override val centerX: Double = 0.0,
                                    override val centerY: Double = 0.0,
                                    override val satelliteData: Maybe<T> = Maybe.empty()) : Hexagon<T> {

    override fun setSatelliteData(data: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSatelliteData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
