package com.example.gymapp;


import java.util.List;

public class FoodType {
    private String typeName;
    private List<FoodItem> foodItems;

    public FoodType() {
        // Empty constructor needed for Firestore serialization
    }

    public FoodType(String typeName, List<FoodItem> foodItems) {
        this.typeName = typeName;
        this.foodItems = foodItems;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}
