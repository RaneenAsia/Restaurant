package com.abc.apps.projectdraft;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemName;
    private String description;
    private double price;
    private String catName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Item(String itemName, String description, double price, String catName ){
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.catName = catName;


    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", catName='" + catName + '\'' +
                '}';
    }
}
