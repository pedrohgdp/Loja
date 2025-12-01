package com.example.estoque.repository;

import com.example.estoque.model.Item;

import java.sql.*;
import java.util.ArrayList;

public class CarregarItemDB {

    private final String URL = "jdbc:sqlite:src/main/resources/com/example/estoque/DB/DB.db";

    public ArrayList<Item> preencherListaItens(){
        ArrayList<Item> listaDeItem = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL)){

            String selectItemsFromDb = "SELECT codigo, descricao, preco, quantidade, marca, estante, prateleira FROM itens;";
            PreparedStatement selectItems = conn.prepareStatement(selectItemsFromDb);
            ResultSet resultSet = selectItems.executeQuery();

            while(resultSet.next()){
                String codigo = resultSet.getString("codigo");
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                int quantidade = resultSet.getInt("quantidade");
                String marca = resultSet.getString("marca");
                int estante = resultSet.getInt("estante");
                String prateleira = resultSet.getString("prateleira");

                Item item = new Item(codigo, descricao, preco, quantidade, marca, estante, prateleira);
                listaDeItem.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaDeItem;
    }
}
