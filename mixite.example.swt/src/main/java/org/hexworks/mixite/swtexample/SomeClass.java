package org.hexworks.mixite.swtexample;

import org.hexworks.mixite.core.api.HexagonOrientation;
import org.hexworks.mixite.core.api.HexagonalGrid;
import org.hexworks.mixite.core.api.HexagonalGridBuilder;
import org.hexworks.mixite.core.api.HexagonalGridLayout;
import org.hexworks.mixite.core.api.contract.SatelliteData;

import java.util.Iterator;

public class SomeClass {

    private static final int GRID_HEIGHT = 9;
    private static final int GRID_WIDTH = 9;
    private static final HexagonalGridLayout GRID_LAYOUT = HexagonalGridLayout.RECTANGULAR;
    private static final HexagonOrientation ORIENTATION = HexagonOrientation.FLAT_TOP;
    private static final double RADIUS = 30;

    // ...

    public static void main(String[] args) {
        HexagonalGridBuilder<SatelliteData> builder = new HexagonalGridBuilder<>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);

        HexagonalGrid grid = builder.build();

for (Object hexagon : grid.getHexagons()) {
    // do your stuff
}
    }

}
