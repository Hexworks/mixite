package org.hexworks.mixite2.core

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.Hexagon
import org.hexworks.mixite2.core.api.Point
import org.hexworks.mixite2.core.api.Rectangle
import org.hexworks.mixite2.core.api.contract.SatelliteData

class HexagonStub<T: SatelliteData>(override val id: String = "",
                                    override val points: List<Point> = listOf(),
                                    override val vertices: List<Double> = listOf(),
                                    override val externalBoundingBox: Rectangle = Rectangle(0.0, 0.0, 0.0, 0.0),
                                    override val internalBoundingBox: Rectangle = Rectangle(0.0, 0.0, 0.0, 0.0),
                                    override val cubeCoordinate: org.hexworks.mixite2.core.api.CubeCoordinate = org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(0, 0),
                                    override val gridX: Int = 0,
                                    override val gridY: Int = 0,
                                    override val gridZ: Int = 0,
                                    override val center: Point = Point.fromPosition(0.0, 0.0),
                                    override val satelliteData: Maybe<T> = Maybe.empty()) : Hexagon<T> {

    override fun setSatelliteData(data: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSatelliteData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
