package org.codetome.hexameter.restexample.dto;

import lombok.Data;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.internal.SharedHexagonData;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a HexagonalGrid in a format which is suitable for drawing.
 */
@Data
public class GridDto {

    private int gridWidth;
    private int gridHeight;
    private double cellWidth;
    private double cellHeight;
    private double cellRadius;
    private HexagonOrientation orientation;
    private HexagonalGridLayout layout;
    private List<HexagonDto> cellData = new ArrayList<>();

    public static GridDto fromGrid(HexagonalGridImpl grid) {
        final SharedHexagonData shd = grid.getSharedHexagonData();

        GridDto result = new GridDto();
        result.setCellWidth(shd.getWidth());
        result.setCellHeight(shd.getHeight());
        result.setCellRadius(shd.getRadius());
        result.setOrientation(shd.getOrientation());
        result.setLayout(grid.getGridLayout());
        result.setGridWidth(grid.getGridWidth());
        result.setGridHeight(grid.getGridHeight());
        grid.getHexagons().forEach(hexagon -> result.addCellData(HexagonDto.fromHexagon(hexagon)));
        return result;
    }

    public void addCellData(HexagonDto hexagonDto) {
        cellData.add(hexagonDto);
    }
}
