package com.example.gymapp;

import com.google.firebase.firestore.PropertyName;
import java.util.List;

public class AddFood {
    private String fullName;
    private List<FoodType> foodTypes;

    public AddFood() {
        // Empty constructor needed for Firestore serialization
    }

    public AddFood(String fullName, List<FoodType> foodTypes) {
        this.fullName = fullName;
        this.foodTypes = foodTypes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }
}


