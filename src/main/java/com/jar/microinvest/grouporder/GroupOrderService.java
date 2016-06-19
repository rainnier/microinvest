package com.jar.microinvest.grouporder;

import com.google.gson.Gson;
import com.jar.microinvest.holdings.Holding;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class GroupOrderService {

    private final DB db;
    private final DBCollection collection;

    public GroupOrderService(DB db) {
        this.db = db;
        this.collection = db.getCollection("groupOrder");
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

    public void createNewGroupOrder(String body) {
        System.out.println("createNewHoldings");
        GroupOrder groupOrder = new Gson().fromJson(body, GroupOrder.class);
        collection.insert(new BasicDBObject("title", groupOrder.getTitle()).append("done", groupOrder.isDone()).append("createdOn", new Date()));
    }

    public GroupOrder find(String id) {
        System.out.println("find");
        return new GroupOrder((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public GroupOrder update(String groupOrderId, String body) {
        System.out.println("update");
        GroupOrder holding = new Gson().fromJson(body, GroupOrder.class);
        collection.update(new BasicDBObject("_id", new ObjectId(groupOrderId)), new BasicDBObject("$set", new BasicDBObject("done", holding.isDone())));
        return this.find(groupOrderId);
    }
}
