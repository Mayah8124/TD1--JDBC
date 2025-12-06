package model;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creation_datetime;

    public Product(int id , String name , double price , LocalDateTime creation_datetime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creation_datetime = creation_datetime;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreation_datetime() {
        return creation_datetime;
    }
}
