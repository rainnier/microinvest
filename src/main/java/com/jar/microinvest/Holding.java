package com.jar.microinvest;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Holding {

    private String id;
    private String title;
    private boolean done;
    private Date createdOn = new Date();

    public Holding(BasicDBObject dbObject) {
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
