package com.jar.microinvest.order;

import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class OrderService {

    private final DB db;
    private final DBCollection collection;

    public OrderService(DB db) {
        this.db = db;
        this.collection = db.getCollection("ordersCollection");
    }

    public List<Order> findAll() {
        System.out.println("findAll");
        List<Order> orderz = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            orderz.add(new Order((BasicDBObject) dbObject));
        }
        System.out.println(orderz);
        return orderz;
    }

    public void createNewOrder(String body) {
        System.out.println("createNewOrder");
        Order order = new Gson().fromJson(body, Order.class);
        collection.insert(new BasicDBObject("stock", order.getStock()).append("done", order.isDone()).append("createdOn", new Date())
                .append("amount", order.getAmount().toPlainString()) .append("transactionType", "BUY")
        );
    }

    public Order find(String id) {
        System.out.println("find");
        return new Order((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public Order update(String orderId, String body) {
        System.out.println("update");
        Order order = new Gson().fromJson(body, Order.class);
        collection.update(new BasicDBObject("_id", new ObjectId(orderId)), new BasicDBObject("$set", new BasicDBObject("done", order.isDone())));
        return this.find(orderId);
    }
}
