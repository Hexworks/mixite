package org.hexworks.mixite.core.api.defaults

import org.hexworks.mixite.core.api.contract.SatelliteData

/**
 * Convenience class implementing SatelliteData.
 */
open class DefaultSatelliteData(
        override var passable: Boolean = false,
        override var opaque: Boolean = false,
        override var movementCost: Double = 0.toDouble()) : SatelliteData
