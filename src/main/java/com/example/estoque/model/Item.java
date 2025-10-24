package com.example.estoque.model;

public class Item {
    private String code;
    private String description;
    private Double price;
    private Integer amount;
    private String brand;
    private Integer shelf;
    private String shelfLevel;

    public Item(String code, String description, double price, Integer amount, String brand, Integer shelf, String shelfLevel) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.brand = brand;
        this.shelf = shelf;
        this.shelfLevel = shelfLevel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

    public String getShelfLevel() {
        return shelfLevel;
    }

    public void setShelfLevel(String shelfLevel) {
        this.shelfLevel = shelfLevel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", brand='" + brand + '\'' +
                ", shelf=" + shelf +
                ", shelfLevel='" + shelfLevel + '\'' +
                '}';
    }
}
