package com.jar.microinvest.user;

import com.google.gson.Gson;
import com.jar.microinvest.order.Order;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class UserService {

    private final DB db;
    private final DBCollection collection;

    public UserService(DB db) {
        this.db = db;
        this.collection = db.getCollection("userCollection");
    }

    public List<User> findAll() {
        System.out.println("findAll");
        List<User> users = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            users.add(new User((BasicDBObject) dbObject));
        }
        System.out.println(users);
        return users;
    }

    public void createNewUser(String body) {

        System.out.println("createNewUser");
        System.out.println(body);
        User user = new Gson().fromJson(body, User.class);
        collection.insert(new BasicDBObject("username", user.getUsername()).
                append("done", user.isDone()).append("createdOn", new Date())
                .append("firstName",user.getFirstName()).append("lastName",user.getLastName())
                .append("middleName",user.getMiddleName()).append("idNumber",user.getIdNumber())
                .append("email",user.getEmail()).append("contactNumber",user.getContactNumber())
                .append("idType",user.getIdType()));
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
