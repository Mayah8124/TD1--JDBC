package com.JDBC.app;

import com.JDBC.controller.DataRetriever;
import com.JDBC.model.Category;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever data = new DataRetriever();

        List<Category> categories = data.getAllCategories();
        for (Category category : categories) {
            System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
        }
    }
}