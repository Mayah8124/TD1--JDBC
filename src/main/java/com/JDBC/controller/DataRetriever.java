package com.JDBC.controller;


import com.JDBC.config.DBConnection;
import com.JDBC.model.Category;
import com.JDBC.model.Product;

import java.sql.*;
import java.time.Instant;
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

        String sql = "SELECT p.id AS product_id , p.name AS product_name , p.price, p.creation_datetime AS creation_datetime, c.id AS category_id , c.name AS category_name FROM Product p " +
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

    public List<Product> getProductByCriteria(String productName, String categoryName , Instant creationMin , Instant creationMax){
        List<Product> productList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                """
                    SELECT 
                        p.id AS product_id, 
                        p.name AS prodcut_name, 
                        p.price AS product_price , 
                        p.creation_datetime AS creation_datetime
                    FROM Product p
                    LEFT JOIN Product_category c 
                    ON p.id = c.product_id
                    WHERE 1=1
                """
        );

        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isBlank()) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isBlank()) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY p.id; ");

        try (Connection conn = dbConnection.getDBConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object value = params.get(i);

                if (value instanceof String) {
                    pstmt.setString(i + 1, (String) value);
                } else if (value instanceof Timestamp) {
                    pstmt.setTimestamp(i + 1, (Timestamp) value);
                }
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
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

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size){
        List<Product> productList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                """
                        SELECT p.id AS product_id,
                               p.name AS product_name,
                               p.price,
                               p.creation_datetime,
                               c.id AS category_id,
                               c.name AS category_name
                        FROM Product p
                        LEFT JOIN Product_category c ON p.id = c.product_id
                        WHERE 1=1
                """
        );

        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isBlank()) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isBlank()) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY p.id; ");
        sql.append("LIMIT ? OFFSET ?");

        int offset = (page - 1) * size;
        params.add(offset);
        params.add(size);

        try (Connection conn = dbConnection.getDBConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ){
            for (int i = 0; i < params.size(); i++) {
                Object value = params.get(i);

                if (value instanceof String) {
                    pstmt.setString(i + 1, (String) value);
                } else if (value instanceof Timestamp) {
                    pstmt.setTimestamp(i + 1, (Timestamp) value);
                } else if (value instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) value);
                }
            }
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
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
