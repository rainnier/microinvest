package com.jar.microinvest.trader;

import com.jar.microinvest.JsonTransformer;

import static spark.Spark.post;

/**
 * Created by IPC_LaptopA on 6/27/2016.
 */
public class TraderResource {

    private static final String API_CONTEXT = "/api/v1";

    private final TraderService traderService;

    public TraderResource(TraderService traderService) {
        this.traderService = traderService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/trader", "application/json", (request, response) -> {
            traderService.fillOrder(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());


    }
}
