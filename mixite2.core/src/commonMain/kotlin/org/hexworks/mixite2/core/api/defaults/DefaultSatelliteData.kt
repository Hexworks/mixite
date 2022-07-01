package org.hexworks.mixite2.core.api.defaults

import org.hexworks.mixite2.core.api.contract.SatelliteData

/**
 * Convenience class implementing SatelliteData.
 */
open class DefaultSatelliteData(
        override var isPassable: Boolean = false,
        override var isOpaque: Boolean = false,
        override var movementCost: Double = 0.toDouble()) : SatelliteData
