package org.codetome.hexameter.restexample.dto;

import lombok.Data;
import org.codetome.hexameter.core.internal.GridData;
import org.codetome.hexameter.core.internal.impl.HexagonalGridImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a HexagonalGrid in a format which is suitable for drawing.
 */
@Data
public class GridDto {

    private GridData gridData;
    private List<HexagonDto> cellData = new ArrayList<>();

    public static GridDto fromGrid(HexagonalGridImpl grid) {
        GridDto result = new GridDto();
        result.setGridData(grid.getGridData());
        grid.getHexagons().forEach(hexagon -> result.addCellData(HexagonDto.fromHexagon(hexagon)));
        return result;
    }

    public void addCellData(HexagonDto hexagonDto) {
        cellData.add(hexagonDto);
    }
}
