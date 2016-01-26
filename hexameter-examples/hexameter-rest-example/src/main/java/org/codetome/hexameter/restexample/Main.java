package org.codetome.hexameter.restexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {

    private static final int HTTP_BAD_REQUEST = 400;

    @Data
    static class HexagonBuilderPayload {
        private int gridWidth;
        private int gridHeight;
        private double radius;
        private HexagonOrientation orientation = POINTY_TOP;
        private HexagonalGridLayout gridLayout = RECTANGULAR;

        public boolean isValid() {
            return gridWidth > 0 && gridHeight > 0 && radius > 0 && orientation != null && gridLayout != null;
        }
    }

    public static class Model {

        private AtomicInteger nextId = new AtomicInteger(1);

        private Map<Integer, HexagonalGrid> grids = new HashMap<>();

        public int createGrid(HexagonBuilderPayload payload) {
            int id = nextId.incrementAndGet();
            HexagonalGrid grid = new HexagonalGridBuilder()
                    .setGridHeight(payload.getGridHeight())
                    .setGridWidth(payload.getGridWidth())
                    .setGridLayout(payload.getGridLayout())
                    .setOrientation(payload.getOrientation())
                    .setRadius(payload.getRadius()).build();
            grids.put(id, grid);
            return id;
        }

        public HexagonalGrid getGridById(Integer id) {
            return grids.get(id);
        }
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Model model = new Model();

        post("/grids", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                HexagonBuilderPayload payload = mapper.readValue(request.body(), HexagonBuilderPayload.class);
                if (!payload.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                int id = model.createGrid(payload);
                response.status(200);
                response.type("application/json");
                return id;
            } catch (Exception e) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        });

        get("/grids/:id", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getGridById(Integer.parseInt(request.params(":id"))));
        });

    }


    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
