package com.JDBC.app;

import com.JDBC.controller.DataRetriever;
import com.JDBC.model.Category;
import com.JDBC.model.Product;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever data = new DataRetriever();

        List<Category> categories = data.getAllCategories();
        for (Category category : categories) {
            System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
        }

        List<Product> products = data.getProductList(1,3);
        for (Product product : products) {
            System.out.println(
                    "ID: " + product.getId() + ", Name: " + product.getName() + ", created at: " + product.getCreationDateTime() + ", Category: " + product.getCategoryName()
            );
        }

        List<Product> filteredProducts = data.getProductByCriteria("laptop", "informatique" , Instant.parse("2024-01-15T00:00:00Z"),null );
        for (Product product : filteredProducts) {
            System.out.println(product.getId() + " , " + product.getName() + " , " + product.getCreationDateTime() + " , " + product.getCategoryName());
        }
    }
}