package com.example.gymapp;

public class SanPhamMain {
    private String imageUrl;
    private String name;
    private String Price;
    public SanPhamMain(String imageUrl, String name, String Price) {
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
    public String getPrice() {
        return Price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String Price) {
        this.Price = Price;
    }
}
