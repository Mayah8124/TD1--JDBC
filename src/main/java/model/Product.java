package model;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDateTime;
    private Category category;

    public Product(int id, String name, Instant creationDateTime, Category category) {
        this.id = id;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public Category getCategory() {
        return category;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    private void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        return category.getName();
    }
}
