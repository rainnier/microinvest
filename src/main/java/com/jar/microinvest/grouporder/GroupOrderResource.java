package com.jar.microinvest.grouporder;


import com.jar.microinvest.JsonTransformer;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class GroupOrderResource {

    private static final String API_CONTEXT = "/api/v1";

    private final GroupOrderService groupOrderService;

    public GroupOrderResource(GroupOrderService groupOrderService) {
        this.groupOrderService = groupOrderService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/gorder", "application/json", (request, response) -> {
            groupOrderService.createNewGroupOrder(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/gorder/:id", "application/json", (request, response)

                -> groupOrderService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/gorder", "application/json", (request, response)

                -> groupOrderService.findAll(), new JsonTransformer());

        Spark.put(API_CONTEXT + "/gorder/:id", "application/json", (request, response)

                -> groupOrderService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
