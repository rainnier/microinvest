package com.jar.microinvest.holdings;

import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class HoldingService {

    private final DB db;
    private final DBCollection collection;

    public HoldingService(DB db) {
        this.db = db;
        this.collection = db.getCollection("holdings");
    }

    public List<Holding> findAll() {
        System.out.println("findAll");
        List<Holding> holdings = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            holdings.add(new Holding((BasicDBObject) dbObject));
        }
        return holdings;
    }

    public void createNewHoldings(String body) {
        System.out.println("createNewHoldings"+ body);
        Holding holding = new Gson().fromJson(body, Holding.class);
        collection.insert(new BasicDBObject("title", holding.getTitle()).append("done", holding.isDone()).append("createdOn", new Date()));
    }

    public Holding find(String id) {
        System.out.println("find");
        return new Holding((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public Holding update(String holdingId, String body) {
        System.out.println("update");
        Holding holding = new Gson().fromJson(body, Holding.class);
        collection.update(new BasicDBObject("_id", new ObjectId(holdingId)), new BasicDBObject("$set", new BasicDBObject("done", holding.isDone())));
        return this.find(holdingId);
    }
}
