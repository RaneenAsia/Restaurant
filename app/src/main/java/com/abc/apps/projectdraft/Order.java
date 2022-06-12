package com.abc.apps.projectdraft;
import java.io.Serializable;

public class Order implements Serializable {
    int quantity;
    Item item;

    public Order(){

    }
    public Order(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return quantity + " "+item.getItemName();
    }
}
