package com.jar.microinvest.holdings;


import com.jar.microinvest.JsonTransformer;
import spark.Spark;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class HoldingResource {

    private static final String API_CONTEXT = "/api/v1";

    private final HoldingService holdingService;

    public HoldingResource(HoldingService holdingService) {
        this.holdingService = holdingService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/holdings", "application/json", (request, response) -> {
            holdingService.createNewHoldings(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/holdings/:id", "application/json", (request, response)

                -> holdingService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/holdings", "application/json", (request, response)

                -> holdingService.findAll(), new JsonTransformer());

        Spark.put(API_CONTEXT + "/holdings/:id", "application/json", (request, response)

                -> holdingService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
