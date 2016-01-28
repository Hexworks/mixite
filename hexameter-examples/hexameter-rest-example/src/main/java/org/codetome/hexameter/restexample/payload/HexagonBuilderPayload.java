package org.codetome.hexameter.restexample.payload;

import lombok.Data;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

@Data
public class HexagonBuilderPayload {
    private Integer id;
    private int gridWidth;
    private int gridHeight;
    private double radius;
    private HexagonOrientation orientation = POINTY_TOP;
    private HexagonalGridLayout gridLayout = RECTANGULAR;
}
