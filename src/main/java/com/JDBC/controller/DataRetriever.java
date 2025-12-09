package com.JDBC.controller;


import com.JDBC.config.DBConnection;
import com.JDBC.model.Category;
import com.JDBC.model.Product;

import java.sql.*;
import java.util.*;

public class DataRetriever {

    private final DBConnection dbConnection = new DBConnection();

    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();

        String sql = "SELECT id , name , product_id FROM Product_category";

        try (Connection conn = dbConnection.getDBConnection();
             Statement stmnt = conn.createStatement();
             ResultSet rs = stmnt.executeQuery(sql);) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categoryList.add(category);
            }
        }
        catch ( SQLException e) {
            System.err.println("Error while retrieving category list " + e.getMessage());
            e.printStackTrace();
        }

        return categoryList;
    }

    public List<Product> getProductList(int page , int size){
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT p.id , p.name , p.price , p.creation_datetime , c.name AS category_name FROM Product p " +
                "LEFT JOIN Product_category c ON p.id = c.product_id ORDER BY p.id LIMIT ? OFFSET ?";

        if (page < 1) {
            page = 1;
        }

        int offset = (page - 1) * size;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

                 pstmt.setInt(1, size);
                 pstmt.setInt(2, offset);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                productList.add(product);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
