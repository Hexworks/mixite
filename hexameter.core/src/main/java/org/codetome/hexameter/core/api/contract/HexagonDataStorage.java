package org.codetome.hexameter.core.api.contract;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.backport.Optional;

/**
 * This interface represents all storage operations which are needed for a working
 * {@link HexagonalGrid}.
 * @param <T> the type of the stored {@link SatelliteData} implementation
 */
public interface HexagonDataStorage<T extends SatelliteData> {

    /**
     * Adds a {@link CubeCoordinate} for this grid without any {@link SatelliteData}.
     * Does not overwrite the coordinate if it is already present.
     */
    void addCoordinate(CubeCoordinate cubeCoordinate);

    /**
     * Adds a {@link CubeCoordinate} for this grid with {@link SatelliteData}.
     * Overwrites previous {@link SatelliteData} if it was present.
     * @return true if overwrote data false otherwise.
     */
    boolean addCoordinate(CubeCoordinate cubeCoordinate, T satelliteData);

    /**
     * Gets the {@link SatelliteData} stored on a {@link CubeCoordinate} if present.
     * Also returns empty {@link Optional} when <code>cubeCoordinate</code> is not present.
     * @return optional {@link SatelliteData}.
     */
    Optional<T> getSatelliteDataBy(CubeCoordinate cubeCoordinate);

    /**
     * Tells whether there is a {@link Hexagon} on the given {@link CubeCoordinate} or not.
     * @return true if present false if not
     */
    boolean containsCoordinate(CubeCoordinate cubeCoordinate);

    /**
     * Tells whether there is {@link SatelliteData} stored for a {@link CubeCoordinate} or not.
     * Also returns false if <code>cubeCoordinate</code> is not present in the storage.
     */
    boolean hasDataFor(CubeCoordinate cubeCoordinate);

    /**
     * Returns all coordinates which are stored in this object.
     */
    Iterable<CubeCoordinate> getCoordinates();

    /**
     * Clears the {@link SatelliteData} for the given {@link CubeCoordinate}.
     * @return true if the storage was changed false otherwise.
     */
    boolean clearDataFor(CubeCoordinate cubeCoordinate);

}
