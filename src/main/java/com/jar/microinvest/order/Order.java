package com.jar.microinvest.order;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Order {

    private String id;
    private String stock;
    private BigDecimal amount;
    private String transactionType; //ALWAYS BUY if from order Always sell if from Holdings
    private boolean done;
    private Date createdOn = new Date();

    public Order(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.stock = dbObject.getString("stock");
        if(dbObject.getString("amount")!=null){
            try {
                this.amount = new BigDecimal(dbObject.getString("amount"));
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            this.amount = BigDecimal.ZERO;
        }

        this.transactionType = dbObject.getString("transactionType");
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
