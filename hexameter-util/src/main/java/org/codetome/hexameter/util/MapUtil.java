package org.codetome.hexameter.util;

import com.google.gson.GsonBuilder;
import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.internal.SharedHexagonData;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;

/**
 * This class can be used to Import/Export maps from/to hexameter.
 */
public final class MapUtil {

    private enum CellDataKeys {
        GRID_X,
        GRID_Z,
        IS_PASSABLE,
        MOVEMENT_COST,
        TILESET_ID;
    }

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

    /**
     * Imports a map.
     */
    public static HexagonalGrid importMap(String mapJson) {
        MapData mapData = new GsonBuilder().create().fromJson(mapJson, MapData.class);

        HexagonalGrid grid = new HexagonalGridBuilder()
                .setGridHeight(mapData.getHeight())
                .setGridWidth(mapData.getWidth())
                .setRadius(mapData.getRadius())
                .setOrientation(mapData.getOrientation())
                .setGridLayout(mapData.getLayout())
                .build();

        mapData.getCells().forEach(cell -> {
            MapSatelliteData msd = new MapSatelliteData();
            msd.setTilesetId(cell[CellDataKeys.TILESET_ID.ordinal()]);
            msd.setMovementCost(Double.parseDouble(cell[CellDataKeys.MOVEMENT_COST.ordinal()]));
            msd.setPassable("0".equals(cell[CellDataKeys.IS_PASSABLE.ordinal()]) ? false : true);

            int gridX = Integer.parseInt(cell[CellDataKeys.GRID_X.ordinal()]);
            int gridZ = Integer.parseInt(cell[CellDataKeys.GRID_Z.ordinal()]);
            grid.getByAxialCoordinate(AxialCoordinate.fromCoordinates(gridX, gridZ)).get().setSatelliteData(msd);
        });

        return grid;
    }

    private static String[] extractCellData(Hexagon hex) {
        MapSatelliteData sd = hex.<MapSatelliteData>getSatelliteData().get();
        String[] result = new String[5];
        result[CellDataKeys.GRID_X.ordinal()] = Integer.toString(hex.getGridX());
        result[CellDataKeys.GRID_Z.ordinal()] = Integer.toString(hex.getGridZ());
        result[CellDataKeys.IS_PASSABLE.ordinal()] = sd.isPassable() ? "1" : "0";
        result[CellDataKeys.MOVEMENT_COST.ordinal()] = Double.toString(sd.getMovementCost());
        result[CellDataKeys.TILESET_ID.ordinal()] = sd.getTilesetId();
        return result;
    }
}
