package com.example.estoque.repository;

import com.example.estoque.model.Item;

import java.sql.*;

public class AddNovoItem {
    private final String URL = "jdbc:sqlite:src/main/resources/com/example/estoque/DB/DB.db";

    public void addNewItemOnDB(Item item){

        String adicionarNovoItemQuery = "INSERT INTO itens (codigo, descricao, preco, quantidade, marca, estante, prateleira) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String checarSeUmItemExisteQuery = "SELECT quantidade FROM itens WHERE codigo = ?;";
        String atualizarQuantidadeSeExisteQuery = "UPDATE itens SET quantidade = quantidade + ? WHERE codigo = ?;";

        try(Connection conn = DriverManager.getConnection(URL)){

            //Verifico se existe no db
            PreparedStatement checarSeExiste = conn.prepareStatement(checarSeUmItemExisteQuery);
            checarSeExiste.setString(1, item.getCodigo());
            ResultSet resultSet = checarSeExiste.executeQuery();


            if(resultSet.next()){
                PreparedStatement updateAmount = conn.prepareStatement(atualizarQuantidadeSeExisteQuery);
                updateAmount.setInt(1, item.getQuantidade());
                updateAmount.setString(2, item.getCodigo());
                updateAmount.executeUpdate();
            }else{
                PreparedStatement insertStatement = conn.prepareStatement(adicionarNovoItemQuery);
                insertStatement.setString(1, item.getCodigo());
                insertStatement.setString(2, item.getDescricao());
                insertStatement.setDouble(3, item.getPreco());
                insertStatement.setInt(4, item.getQuantidade());
                insertStatement.setString(5, item.getMarca());
                insertStatement.setInt(6, item.getEstante());
                insertStatement.setString(7, item.getPrateleira());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
