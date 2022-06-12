package com.abc.apps.projectdraft;

import java.io.Serializable;

public class order_class implements Serializable {
    private int orderID;
    private String customerId;
    private String order;
    private String status;
    private Double payment;
    private String Address;

    public order_class(int orderID, String customerId, String order, String status, Double payment, String address) {
        this.orderID = orderID;
        this.customerId = customerId;
        this.order = order;
        this.status = status;
        this.payment = payment;
        Address = address;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderId(int orderId) {
        this.orderID = orderId;
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

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return orderID +
                ", " + customerId +
                "," + order +
                ","+ status+ "," + payment +
                "," + Address;
    }
}
