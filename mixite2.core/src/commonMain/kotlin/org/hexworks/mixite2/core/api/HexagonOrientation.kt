package org.hexworks.mixite2.core.api

/**
 * Enum representing the possible orientations of a [Hexagon]. The names
 * are self-explanatory.
 */
enum class HexagonOrientation(val coordinateOffset: Float) {

    POINTY_TOP(0.5f),
    FLAT_TOP(0f)

}
