package org.codetome.hexameter.restexample;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.codetome.hexameter.restexample.dto.GridDto;
import org.codetome.hexameter.restexample.model.Model;
import org.codetome.hexameter.restexample.payload.HexagonBuilderPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

public class Main {

    private static final String BAD_REQUEST = "Bad request";
    private static final String APPLICATION_JSON = "application/json";
    private static final int DEFAULT_PORT = 4567;

    private Main() {}

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        port(getHerokuAssignedPort());
        Model model = new Model();

        Spark.staticFileLocation("/templates");

        Map<String, Object> map = new HashMap<>();
        if(isRunningOnHeroku()) {
            map.put("appURL", "http://hexameter-rest-example.herokuapp.com");
        } else {
            map.put("appURL", "http://localhost:" + DEFAULT_PORT);
        }


        get("/", (rq, rs) -> new ModelAndView(map, "index"), new ThymeleafTemplateEngine());

        post("/grids", (request, response) -> {
            HexagonBuilderPayload payload = new ObjectMapper().readValue(request.body(), HexagonBuilderPayload.class);
            int id = model.createGrid(payload);
            response.status(201);
            response.type(APPLICATION_JSON);
            return id;
        });

        put("/grids", (request, response) -> {
            HexagonBuilderPayload payload = new ObjectMapper().readValue(request.body(), HexagonBuilderPayload.class);
            int id = model.replaceGrid(payload);
            response.status(201);
            response.type(APPLICATION_JSON);
            return id;
        });

        get("/grids/getGridForDrawing/:id", (request, response) -> {
            response.status(200);
            response.type(APPLICATION_JSON);
            return dataToJson(GridDto.fromGrid(model.getGridById(parseInt(request.params(":id")))));
        });

        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body("Resource not found");
        });

        exception(IOException.class, (e, request, response) -> {
            response.status(400);
            logger.error("Fail", e);
            response.body(BAD_REQUEST);
        });

        exception(JsonParseException.class, (e, request, response) -> {
            response.status(400);
            logger.error("Fail", e);
            response.body(BAD_REQUEST);
        });

        exception(JsonMappingException.class, (e, request, response) -> {
            response.status(400);
            logger.error("Fail", e);
            response.body(BAD_REQUEST);
        });

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

    }


    private static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    private static boolean isRunningOnHeroku() {
        return new ProcessBuilder().environment().get("PORT") != null;
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
