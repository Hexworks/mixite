package org.codetome.hexameter.util;

import com.google.gson.GsonBuilder;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.internal.SharedHexagonData;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;

/**
 * This class can be used to Import/Export maps from/to hexameter.
 */
public class MapUtil {

    /**
     * Exports the map.
     */
    public static String exportMap(HexagonalGrid grid, String name, String tilesetUrl) {
        MapData result = new MapData();
        HexagonalGridImpl hgi = (HexagonalGridImpl) grid;
        SharedHexagonData shd = hgi.getSharedHexagonData();
        result.setRadius(shd.getRadius());
        result.setWidth(hgi.getGridWidth());
        result.setHeight(hgi.getGridHeight());

        result.setLayout(hgi.getGridLayout());
        result.setOrientation(shd.getOrientation());

        result.setName(name);
        result.setTilesetUrl(tilesetUrl);
        result.setVersion("1.0.0"); // TODO: externalize this

        grid.getHexagons().forEach(hex -> {
            if (hex.getSatelliteData().isPresent()) {
                result.addCellData(extractCellData(hex));
            }
        });

        return new GsonBuilder().create().toJson(result);
    }

    private static String[] extractCellData(Hexagon hex) {
        MapSatelliteData sd = hex.<MapSatelliteData>getSatelliteData().get();
        return new String[]{Integer.toString(hex.getGridX()),
                Integer.toString(hex.getGridZ()),
                sd.isPassable() ? "1" : "0",
                Double.toString(sd.getMovementCost()),
                sd.getTilesetId()};
    }
}
