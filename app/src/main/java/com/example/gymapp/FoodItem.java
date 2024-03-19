package com.example.gymapp;
public class FoodItem {
    private String itemName;


    public FoodItem() {
        // Empty constructor needed for Firestore serialization
    }

    public FoodItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


}