package com.jar.microinvest.trader;

import com.google.gson.Gson;
import com.jar.microinvest.holdings.Holding;
import com.jar.microinvest.order.Order;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IPC_LaptopA on 6/27/2016.
 */
public class TraderService {

    private final DB db;
    private final DBCollection collectionOrder;
    private final DBCollection collectionHolding;

    public TraderService(DB db) {
        this.db = db;
        this.collectionOrder = db.getCollection("ordersCollection");
        this.collectionHolding = db.getCollection("holdingsCollection");
    }


    public void fillOrder(String body) {

        System.out.println("fillOrder");
        System.out.println(body);
        Order orderToFill = new Gson().fromJson(body, Order.class);
        String orderToFillStock = orderToFill.getStock();
        System.out.println("orderToFill.getTotal():"+orderToFill.getTotal());

        List<Order> orderz = new ArrayList<>();
        BigDecimal totalOrderAmountForStock = BigDecimal.ZERO;

        DBCursor dbObjects = collectionOrder.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            Order order = new Order((BasicDBObject) dbObject);
            String stock = order.getStock();
            if(orderToFillStock.equalsIgnoreCase(stock)){
                orderz.add(order);
                totalOrderAmountForStock = totalOrderAmountForStock.add(order.getTotal());
            }
        }

        //Use all orders to fill trader's trade
        if(orderToFill.getTotal().compareTo(totalOrderAmountForStock)==0){


        } else if(orderToFill.getTotal().compareTo(totalOrderAmountForStock)==-1){
            //will not use all buying power for trader's trade

        } else if(orderToFill.getTotal().compareTo(totalOrderAmountForStock)==1){
            //not enough buying power for trader's trade

        }

        Holding holding = new Gson().fromJson(body, Holding.class);
        collectionHolding.insert(new BasicDBObject("stock", holding.getStock()).append("done", holding.isDone()).append("createdOn", new Date()));

        //Get order by orderId
        //Update done field
        Order order = new Order((BasicDBObject) collectionOrder.findOne(new BasicDBObject("_id", new ObjectId(holding.getOrderId()))));
        collectionOrder.update(new BasicDBObject("_id", new ObjectId(holding.getOrderId())), new BasicDBObject("$set", new BasicDBObject("done", holding.isDone())));

        //TODO:Find order near the orderToFill Total
        //TODO:Create Holdings
        //TODO:Update Order Status to Done if ALL MONEY IS SPENT
        //TODO:Update Total Cost
        //TODO:Add new field Original Cost -> this does not change based on original order

    }

    public void createNewHoldings(Order order) {
        System.out.println("createNewHoldings"+ order);
        Holding holding = new Holding(order);
        collectionHolding.insert(new BasicDBObject("stock", holding.getStock()).append("done", holding.isDone()).append("createdOn", new Date()));
    }






}
