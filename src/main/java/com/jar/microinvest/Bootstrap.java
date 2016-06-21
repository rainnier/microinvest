package com.jar.microinvest;

import com.jar.microinvest.holdings.HoldingResource;
import com.jar.microinvest.holdings.HoldingService;
import com.mongodb.*;

import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;
import static spark.SparkBase.staticFileLocation;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Bootstrap {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 9090;

    public static void main(String[] args) throws Exception {
        System.out.println("main");
        setIpAddress(IP_ADDRESS);
        System.out.println(IP_ADDRESS);
        setPort(PORT);
        System.out.println(PORT);
        staticFileLocation("/public");
        System.out.println("/public");
        new HoldingResource(new HoldingService(mongo()));
//        new GroupHoldingsResource(new GroupHoldingsService(mongo()));
//        new OrderResource(new OrderService(mongo()));
//        new GroupOrderResource(new GroupOrderService(mongo()));
        enableDebugScreen();
    }

    private static DB mongo() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        if (host == null) {
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDB("microinvest");
        }
        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String dbname = System.getenv("OPENSHIFT_APP_NAME");
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
        if (db.authenticate(username, password.toCharArray())) {
            return db;
        } else {
            throw new RuntimeException("Not able to authenticate with MongoDB");
        }
    }
}
