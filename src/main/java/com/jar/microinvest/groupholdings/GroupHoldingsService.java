package com.jar.microinvest.groupholdings;

import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class GroupHoldingsService {

    private final DB db;
    private final DBCollection collection;

    public GroupHoldingsService(DB db) {
        this.db = db;
        this.collection = db.getCollection("groupholdings");
    }

    public List<GroupHoldings> findAll() {
        System.out.println("findAll");
        List<GroupHoldings> holdings = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            holdings.add(new GroupHoldings((BasicDBObject) dbObject));
        }
        return holdings;
    }

    public void createNewGroupHoldings(String body) {
        System.out.println("createNewGroupHoldings");
        GroupHoldings groupHoldings = new Gson().fromJson(body, GroupHoldings.class);
        collection.insert(new BasicDBObject("title", groupHoldings.getTitle()).append("done", groupHoldings.isDone()).append("createdOn", new Date()));
    }

    public GroupHoldings find(String id) {
        System.out.println("find");
        return new GroupHoldings((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public GroupHoldings update(String groupHoldingId, String body) {
        System.out.println("update");
        GroupHoldings groupHoldings = new Gson().fromJson(body, GroupHoldings.class);
        collection.update(new BasicDBObject("_id", new ObjectId(groupHoldingId)), new BasicDBObject("$set", new BasicDBObject("done", groupHoldings.isDone())));
        return this.find(groupHoldingId);
    }
}
