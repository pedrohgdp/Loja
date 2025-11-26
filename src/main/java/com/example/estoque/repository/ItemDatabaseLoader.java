package com.example.estoque.repository;

import com.example.estoque.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDatabaseLoader {

    private final String URL = "jdbc:sqlite:src/main/resources/com/example/estoque/DB/DB.db";

    public ArrayList<Item> fillItemsList(){
        ArrayList<Item> listItems = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL)){

            String selectItemsFromDb = "SELECT code, description, price, amount, brand, shelf, shelfLevel FROM items;";
            PreparedStatement selectItems = conn.prepareStatement(selectItemsFromDb);
            ResultSet resultSet = selectItems.executeQuery();

            while(resultSet.next()){
                String code = resultSet.getString("code");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int amount = resultSet.getInt("amount");
                String brand = resultSet.getString("brand");
                int shelf = resultSet.getInt("shelf");
                String shelfLevel = resultSet.getString("shelfLevel");

                Item item = new Item(code, description, price, amount, brand, shelf, shelfLevel);
                listItems.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listItems;
    }
}
