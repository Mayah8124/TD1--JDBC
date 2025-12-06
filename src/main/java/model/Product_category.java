package model;

public class Product_category {
    private int id;
    private String name;
    private Product product_id;

    public Product_category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product getProduct_id() {
        return product_id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }
}
