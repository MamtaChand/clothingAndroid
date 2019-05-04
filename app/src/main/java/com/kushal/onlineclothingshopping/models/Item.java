package com.kushal.onlineclothingshopping.models;

import android.graphics.drawable.Drawable;

public class Item {

    private String itemName;
    private String itemPrice;
    private int itemImageName;
    private String itemDescription;

    public Item(){ }

    public Item(String itemName, String itemPrice, int itemImageName, String itemDescription) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemImageName() {
        return itemImageName;
    }

    public void setItemImageName(int itemImageName) {
        this.itemImageName = itemImageName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
