package com.jar.microinvest.groupholdings;


import com.jar.microinvest.JsonTransformer;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class GroupHoldingsResource {

    private static final String API_CONTEXT = "/api/v1";

    private final GroupHoldingsService groupHoldingsService;

    public GroupHoldingsResource(GroupHoldingsService groupHoldingsService) {
        this.groupHoldingsService = groupHoldingsService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/gholdings", "application/json", (request, response) -> {
            groupHoldingsService.createNewGroupHoldings(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/gholdings/:id", "application/json", (request, response)

                -> groupHoldingsService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/gholdings", "application/json", (request, response)

                -> groupHoldingsService.findAll(), new JsonTransformer());

        Spark.put(API_CONTEXT + "/gholdings/:id", "application/json", (request, response)

                -> groupHoldingsService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
