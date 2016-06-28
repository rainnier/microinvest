package com.jar.microinvest.holdings;

import com.jar.microinvest.order.Order;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Holding {

    private String id;
    private boolean done;
    private Date createdOn = new Date();

    private String orderId;
    private String stock;

    private String type;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal total;
    private BigDecimal originalHoldingAmount;




    public Holding(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");

        this.orderId = ((ObjectId) dbObject.get("orderId")).toString();
        this.stock = dbObject.getString("stock");
        this.type = dbObject.getString("type");
        this.quantity = this.getBigDecimalVersion(dbObject.getString("quantity"));
        this.price = this.getBigDecimalVersion(dbObject.getString("price"));
        this.total = this.getBigDecimalVersion(dbObject.getString("total"));
        this.originalHoldingAmount = this.getBigDecimalVersion(dbObject.getString("total"));

    }

    public Holding(Order order) {
        this.done = Boolean.FALSE;
        this.createdOn = new Date();

        this.orderId = order.getId();
        this.stock = order.getStock();
        this.type = order.getType();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.total = order.getTotal();
        this.originalHoldingAmount = order.getTotal();

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

    public String getStock() {
        return stock;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getOriginalHoldingAmount() {
        return originalHoldingAmount;
    }

    public void setOriginalHoldingAmount(BigDecimal originalHoldingAmount) {
        this.originalHoldingAmount = originalHoldingAmount;
    }
}
