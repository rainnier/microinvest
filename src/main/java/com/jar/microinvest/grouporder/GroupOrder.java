package com.jar.microinvest.grouporder;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class GroupOrder {

    private String id;
    private String stock;
    private String transactionType;
    private String title;
    private long amountInBucket;
    private long stockPrice;
    private boolean done;
    private Date createdOn = new Date();

    public GroupOrder(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.title = dbObject.getString("title");
        this.done = dbObject.getBoolean("done"); 
        this.createdOn = dbObject.getDate("createdOn");
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
