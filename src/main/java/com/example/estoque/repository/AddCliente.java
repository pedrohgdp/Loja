package com.example.estoque.repository;

import com.example.estoque.model.Cliente;

import java.sql.*;

public class AddCliente {
    private final String URL = "jdbc:sqlite:src/main/resources/com/example/estoque/DB/DB.db";

    public boolean addCliente(Cliente cliente) {
        String checarCliente = "SELECT codigo FROM cliente WHERE cpf_cnpj = ?";
        String inserirCliente = """
                    INSERT INTO cliente (nome, limite_usado, limite_total, cpf_cnpj)
                    VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement checar = conn.prepareStatement(checarCliente)) {

            checar.setString(1, cliente.getCpfCnpj());
            ResultSet rs = checar.executeQuery();

            if (rs.next()) {
                System.out.println("Já existe esse cliente!");
                return false;
            }

            try (PreparedStatement inserir = conn.prepareStatement(inserirCliente)) {
                inserir.setString(1, cliente.getNome());
                inserir.setDouble(2, 0.0);
                inserir.setDouble(3, cliente.getLimiteTotal());
                inserir.setString(4, cliente.getCpfCnpj());
                inserir.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
