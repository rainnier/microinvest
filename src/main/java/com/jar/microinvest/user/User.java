package com.jar.microinvest.user;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by
 */
public class User {

    private String id;
    private String username;
    private boolean done;
    private Date createdOn = new Date();

    private String firstName;
    private String lastName;
    private String middleName;
    private String idNumber;
    private String idType;
    private String email;
    private String contactNumber;


    public User(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.username = dbObject.getString("username");
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
        this.firstName = dbObject.getString("firstName");
        this.lastName = dbObject.getString("lastName");
        this.middleName = dbObject.getString("middleName");
        this.idNumber = dbObject.getString("idNumber");
        this.idType = dbObject.getString("idType");
        this.email = dbObject.getString("email");
        this.contactNumber = dbObject.getString("contactNumber");
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

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
