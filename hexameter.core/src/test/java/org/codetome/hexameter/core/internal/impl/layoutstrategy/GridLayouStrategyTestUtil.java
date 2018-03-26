package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

public class GridLayouStrategyTestUtil {

    private static final int RADIUS = 30;
    private static final int GRID_WIDTH = 3;
    private static final int GRID_HEIGHT = 3;
    private static final HexagonOrientation ORIENTATION = HexagonOrientation.POINTY_TOP;

    public static HexagonalGridBuilder fetchDefaultBuilder() {
        return new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setRadius(RADIUS).setOrientation(ORIENTATION);
    }
}
