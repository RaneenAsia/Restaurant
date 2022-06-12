package com.abc.apps.projectdraft;

import java.io.Serializable;

public class order implements Serializable {
    private String customerId;
    private String order;
    private String status;
    private double payment;
    private String Address;
    private int orderID;

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public order(int orderID, String customerId, String order, String status, double payment, String address) {
        this.customerId = customerId;
        this.order = order;
        this.status = status;
        this.payment = payment;
        Address = address;
        this.orderID=orderID;

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return customerId+"";
    }
    public String getString() {
        return
                 orderID+","+customerId +
                "," + order + "," +status+","+payment+","+Address;

    }
}
