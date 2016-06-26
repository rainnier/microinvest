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
    private boolean done;
    private Date createdOn = new Date();
    
    private String type;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal total;
    private BigDecimal originalOrderAmount;

    public Order(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.stock = dbObject.getString("stock");        
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
        this.type = dbObject.getString("type");
        this.quantity = this.getBigDecimalVersion(dbObject.getString("quantity"));
        this.price = this.getBigDecimalVersion(dbObject.getString("price"));
        this.total = this.getBigDecimalVersion(dbObject.getString("total"));
        this.originalOrderAmount = this.getBigDecimalVersion(dbObject.getString("total"));
    }

    public BigDecimal getBigDecimalVersion(String bdString){
        if(bdString!=null){
            try {
                BigDecimal tmp =  new BigDecimal(bdString);
                return tmp;
            } catch (Exception e){
                e.printStackTrace();
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }


    }


    public String getType() {
        return type;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public BigDecimal getTotal() {
        return total;
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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getOriginalOrderAmount() {
        return originalOrderAmount;
    }

    public void setOriginalOrderAmount(BigDecimal originalOrderAmount) {
        this.originalOrderAmount = originalOrderAmount;
    }
}
