package com.example.fragments.pojo;

public class Product {
    private String name;
    private String description;
    private int stockCount;
    private String id;

    public Product() {
    }

    public Product(String id, String name, String description, int stockCount) {
        this.name = name;
        this.description = description;
        this.stockCount = stockCount;
        this.id = id;
    }

    public boolean decrementStockCount() {
        setStockCount(stockCount - 1);

        if (stockCount < 0) {
            setStockCount(0);
            return false;
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
}
