package com.JDBC.controller;


import com.JDBC.config.DBConnection;
import com.JDBC.model.Category;

import java.sql.*;
import java.util.*;

public class DataRetriever {

    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();

        String sql = "SELECT id , name , product_id FROM Product_category";

        try (Connection conn = DBConnection.getDBConnection();
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



}
