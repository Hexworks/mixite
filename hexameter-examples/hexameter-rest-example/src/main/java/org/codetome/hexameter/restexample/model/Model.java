package org.codetome.hexameter.restexample.model;

import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;
import org.codetome.hexameter.restexample.payload.HexagonBuilderPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Model {

    private AtomicInteger nextId = new AtomicInteger(1);

    private Map<Integer, HexagonalGridImpl> grids = new HashMap<>();

    public int createGrid(HexagonBuilderPayload payload) {
        int id = nextId.incrementAndGet();
        grids.put(id, buildGrid(payload));
        return id;
    }

    public int replaceGrid(HexagonBuilderPayload payload) {
        grids.put(payload.getId(), buildGrid(payload));
        return payload.getId();
    }

    private HexagonalGridImpl buildGrid(HexagonBuilderPayload payload) {
        HexagonalGrid grid = new HexagonalGridBuilder()
                .setGridHeight(payload.getGridHeight())
                .setGridWidth(payload.getGridWidth())
                .setGridLayout(payload.getGridLayout())
                .setOrientation(payload.getOrientation())
                .setRadius(payload.getRadius()).build();
        return (HexagonalGridImpl) grid;
    }

    public HexagonalGridImpl getGridById(Integer id) {
        return grids.get(id);
    }
}
