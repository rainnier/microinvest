package com.jar.microinvest.order;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Order {

    private String id;
    private String title;
    private boolean done;
    private Date createdOn = new Date();
    
    private long quantity;
    private long price;
    private long total;

    public Order(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.title = dbObject.getString("title");
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
        this.quantity = dbObject.getLong("quantity");
        this.price = dbObject.getLong("price");
        this.total = dbObject.getLong("total");
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
}
