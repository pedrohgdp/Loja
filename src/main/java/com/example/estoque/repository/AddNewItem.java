package com.example.estoque.repository;

import com.example.estoque.model.Item;

import java.sql.*;

public class AddNewItem {
    private final String URL = "jdbc:sqlite:src/main/resources/com/example/estoque/DB/DB.db";

    public void addNewItemOnDB(Item item){

        String insertNewItemQuery = "INSERT INTO items (code, description, price, amount, brand, shelf, shelfLevel) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String checkIfItemExistQuery = "SELECT amount FROM items WHERE code = ?;";
        String updateAmountIfItemExist = "UPDATE items SET amount = amount + ? WHERE code = ?;";

        try(Connection conn = DriverManager.getConnection(URL)){

            //Verifico se existe no db
            PreparedStatement checkIfExist = conn.prepareStatement(checkIfItemExistQuery);
            checkIfExist.setString(1, item.getCode());
            ResultSet resultSet = checkIfExist.executeQuery();


            if(resultSet.next()){
                PreparedStatement updateAmount = conn.prepareStatement(updateAmountIfItemExist);
                updateAmount.setInt(1, item.getAmount());
                updateAmount.setString(2, item.getCode());
                updateAmount.executeUpdate();
            }else{
                PreparedStatement insertStatement = conn.prepareStatement(insertNewItemQuery);
                insertStatement.setString(1, item.getCode());
                insertStatement.setString(2, item.getDescription());
                insertStatement.setDouble(3, item.getPrice());
                insertStatement.setInt(4, item.getAmount());
                insertStatement.setString(5, item.getBrand());
                insertStatement.setInt(6, item.getShelf());
                insertStatement.setString(7, item.getShelfLevel());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
