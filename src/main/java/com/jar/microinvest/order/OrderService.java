package com.jar.microinvest.order;

import com.jar.microinvest.GroupOrderService;

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
    private final DBCollection collection;

    public OrderService(DB db) {
        this.db = db;
        this.collection = db.getCollection("ordersCollection");
        this.gorderCollection = db.getCollection("groupOrder");
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

        Order order = new Gson().fromJson(body, Order.class);
        System.out.println("createOrUpdateGroupOrder");
        
        gorderCollection.update(
           { stock: order.getStock(), type: order.getType(), done: false},
           { $push:
               {
                 stock: order.getStock(),
                 type: order.getType(),
                 done: false,
                 stockPrice: order.getPrice().toPlainString(),
                 amountInBucket: order.getTotal().toPlainString()
               }
           },
           { upsert: true }
        )
        
        System.out.println("createNewOrder");
        System.out.println(body);
        
        collection.insert(new BasicDBObject("title", order.getType())
            .append("stock", order.getStock()).append("type", order.getType())
            .append("quantity", order.getQuantity().toPlainString())
            .append("price", order.getPrice().toPlainString())
            .append("total", order.getTotal().toPlainString())
            .append("done", order.isDone()).append("createdOn", new Date()));
            
        collection.update(new BasicDBObject("_id", new ObjectId(groupOrderId)), new BasicDBObject("$set", new BasicDBObject("done", groupOrder.isDone())));
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
    
    public void remove(String orderId) {
        System.out.println("remove");
        collection.remove(new BasicDBObject("_id", new ObjectId(orderId)));
        
        db.mycollection.update(
            {'_id': ObjectId("5150a1199fac0e6910000002")}, 
            { $pull: { "items" : { id: 23 } } },
        false,
        true 
        );
    }
    
}
