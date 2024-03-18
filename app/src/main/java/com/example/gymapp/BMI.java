package com.example.gymapp;

public class BMI {
    private String imageUrl;
    private String name;
    private String Price;
    public BMI(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.Price = Price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
