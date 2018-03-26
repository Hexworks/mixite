package org.codetome.hexameter.core.api.defaults;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.backport.Optional;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultHexagonDataStorage implements HexagonDataStorage<DefaultSatelliteData> {

    private Map<CubeCoordinate, Optional<DefaultSatelliteData>> storage = new LinkedHashMap<>();

    @Override
    public final void addCoordinate(final CubeCoordinate cubeCoordinate) {
        storage.put(cubeCoordinate, Optional.<DefaultSatelliteData>empty());
    }

    @Override
    public final boolean addCoordinate(final CubeCoordinate cubeCoordinate, final DefaultSatelliteData satelliteData) {
        final Optional<DefaultSatelliteData> previous = storage.put(cubeCoordinate, Optional.of(satelliteData));
        return previous != null;
    }

    @Override
    public final Optional<DefaultSatelliteData> getSatelliteDataBy(final CubeCoordinate cubeCoordinate) {
        return storage.containsKey(cubeCoordinate) ? storage.get(cubeCoordinate) : Optional.<DefaultSatelliteData>empty();
    }

    @Override
    public final boolean containsCoordinate(final CubeCoordinate cubeCoordinate) {
        return storage.containsKey(cubeCoordinate);
    }

    @Override
    public final boolean hasDataFor(final CubeCoordinate cubeCoordinate) {
        return storage.containsKey(cubeCoordinate) && storage.get(cubeCoordinate).isPresent();
    }

    @Override
    public final Iterable<CubeCoordinate> getCoordinates() {
        return storage.keySet();
    }

    @Override
    public final boolean clearDataFor(final CubeCoordinate cubeCoordinate) {
        boolean result = false;
        if (hasDataFor(cubeCoordinate)) {
            result = true;
        }
        storage.put(cubeCoordinate, Optional.<DefaultSatelliteData>empty());
        return result;
    }
}
