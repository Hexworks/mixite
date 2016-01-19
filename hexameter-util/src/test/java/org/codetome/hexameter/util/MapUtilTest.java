package org.codetome.hexameter.util;

import com.google.gson.GsonBuilder;
import org.codetome.hexameter.core.api.*;
import org.junit.Test;

import static java.lang.Integer.valueOf;
import static junit.framework.Assert.assertEquals;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.EMPTY;
import static org.codetome.hexameter.util.MapUtil.exportMap;

public class MapUtilTest {

    private static final String EXPECTED_MAP_NAME = "testmap";
    private static final String EXPECTED_TILESET_URL = "http://example.com";

    private static final int EXPECTED_WIDTH = 0;
    private static final int EXPECTED_HEIGHT = 0;
    private static final double EXPECTED_RADIUS = 40.0;
    private static final HexagonOrientation EXPECTED_ORIENTATION = POINTY_TOP;
    private static final HexagonalGridLayout EXPECTED_LAYOUT = EMPTY;

    private static final String[][] EXPECTED_CELL_DATA = new String[][]{
            {"0", "0", "1", "1.0", "1"},
            {"1", "0", "1", "1.0", "1"},
            {"2", "0", "0", "1.0", "2"},
            {"3", "0", "1", "1.0", "1"},
            {"0", "1", "1", "1.0", "1"},
            {"1", "1", "0", "1.0", "2"},
            {"2", "1", "1", "1.0", "1"},
            {"3", "1", "1", "1.0", "1"},
            {"0", "2", "0", "1.0", "2"},
            {"1", "2", "1", "1.0", "1"},
            {"2", "2", "0", "1.0", "2"},
            {"3", "2", "1", "1.0", "1"}
    };

    private static final String EXPECTED_JSON = "{" +
            "  \"name\": \"" + EXPECTED_MAP_NAME + "\"," +
            "  \"version\": \"1.0.0\"," +
            "  \"width\": " + EXPECTED_WIDTH + "," +
            "  \"height\": " + EXPECTED_HEIGHT + "," +
            "  \"radius\": " + EXPECTED_RADIUS + "," +
            "  \"orientation\": \"" + EXPECTED_ORIENTATION.name() + "\"," +
            "  \"layout\" : \"" + EXPECTED_LAYOUT.name() + "\"," +
            "  \"tilesetUrl\": \"" + EXPECTED_TILESET_URL + "\"," +
            "  \"cells\" : " + new GsonBuilder().create().toJson(EXPECTED_CELL_DATA).toString() + "}";

    @Test
    public void shouldReturnProperJsonWhenExportIsCalled() {
        HexagonalGrid grid = new HexagonalGridBuilder().setGridHeight(EXPECTED_HEIGHT).setGridLayout(EXPECTED_LAYOUT).setGridWidth(EXPECTED_WIDTH).setOrientation(EXPECTED_ORIENTATION).setRadius(EXPECTED_RADIUS).build();

        for(int i = 0; i < EXPECTED_CELL_DATA.length; i++) {
            AxialCoordinate ac = AxialCoordinate.fromCoordinates(valueOf(EXPECTED_CELL_DATA[i][0]), valueOf(EXPECTED_CELL_DATA[i][1]));
            grid.addHexagon(ac);
            MapSatelliteData msd = new MapSatelliteData();
            msd.setPassable("0".equals(EXPECTED_CELL_DATA[i][2]) ? false : true);
            msd.setMovementCost(Double.valueOf(EXPECTED_CELL_DATA[i][3]));
            msd.setTilesetId(EXPECTED_CELL_DATA[i][4]);
            grid.getByAxialCoordinate(ac).get().setSatelliteData(msd);
        }

        final String result = exportMap(grid, EXPECTED_MAP_NAME, EXPECTED_TILESET_URL);

        final MapData expectedJson = new GsonBuilder().create().fromJson(EXPECTED_JSON, MapData.class);
        final MapData resultJson = new GsonBuilder().create().fromJson(result, MapData.class);

        final String expectedJsonString = new GsonBuilder().create().toJson(expectedJson);
        final String resultJsonString = new GsonBuilder().create().toJson(resultJson);

        assertEquals(expectedJsonString, resultJsonString);
    }

}
