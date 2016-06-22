package com.jar.microinvest.user;


import com.jar.microinvest.JsonTransformer;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class UserResource {

    private static final String API_CONTEXT = "/api/v1";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/user", "application/json", (request, response) -> {
            userService.createNewUser(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/user/:id", "application/json", (request, response)

                -> userService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/user", "application/json", (request, response)

                -> userService.findAll(), new JsonTransformer());

        Spark.put(API_CONTEXT + "/user/:id", "application/json", (request, response)

                -> userService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
