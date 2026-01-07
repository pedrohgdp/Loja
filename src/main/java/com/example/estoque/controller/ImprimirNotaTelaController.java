package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.model.Nota;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ImprimirNotaTelaController {

    public void mostrarNota(Nota nota) throws IOException {
        VBox layoutNota = new VBox(10);
        layoutNota.setPadding(new Insets(20));
        layoutNota.setPrefWidth(560);
        layoutNota.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: white;");


        Label nomeLoja = new Label("MINHA LOJA S.A.");
        nomeLoja.setStyle("-fx-font-size: 18pt; -fx-font-weight: bold; -fx-alignment: center;");

        Label dataNota = new Label("DATA: " + nota.getDataNota());
        dataNota.setStyle("-fx-font-style: italic;");

        String valorNumeroNota = nota.getNumeroNota() == null || nota.getNumeroNota().trim().isEmpty() ? "" : nota.getNumeroNota();
        String cliente = nota.getCliente() == null || nota.getCliente().trim().isEmpty() ? "" : nota.getCliente();
        String solicitante = nota.getSolicitante() == null || nota.getSolicitante().trim().isEmpty() ? "" : nota.getSolicitante();

        Label numeroNotaLabel = new Label("NOTA Nº: " + valorNumeroNota);
        Label clienteLabel = new Label("CLIENTE: " + cliente);
        Label solicitanteLabel = new Label("SOLICITANTE: " + solicitante);

        VBox cabecalho = new VBox(5, nomeLoja, dataNota, new Separator(), numeroNotaLabel, clienteLabel, solicitanteLabel, new Separator());


        HBox cabecalhoItens = new HBox(25);
        Label colCodigoH = new Label("CÓDIGO");
        Label colDescricaoH = new Label("DESCRIÇÃO");
        Label colQuantidadeH = new Label("QTD");
        Label colPrecoUnitarioH = new Label("PREÇO UNIT.");
        Label colSubtotalH = new Label("SUBTOTAL");


        colCodigoH.setPrefWidth(80);
        HBox.setHgrow(colDescricaoH, Priority.ALWAYS);
        colQuantidadeH.setPrefWidth(60);
        colPrecoUnitarioH.setPrefWidth(120);
        colSubtotalH.setPrefWidth(120);


        colSubtotalH.setAlignment(Pos.CENTER_RIGHT);

        cabecalhoItens.getChildren().addAll(colCodigoH, colDescricaoH, colQuantidadeH, colPrecoUnitarioH, colSubtotalH);
        cabecalhoItens.setStyle("-fx-font-weight: bold;");


        VBox corpoItens = new VBox(5);
        for (Item item : nota.getItens()) {
            HBox linhaItem = new HBox(25);
            Label colCodigo = new Label(item.getCodigo());
            Label colDescricao = new Label(item.getDescricao());
            Label colQuantidade = new Label(String.valueOf(item.getQuantidadeVenda()));
            Label colPrecoUnitario = new Label(String.format("R$ %.2f", item.getPreco()));
            Label colSubtotal = new Label(String.format("R$ %.2f", item.getTotal()));


            colCodigo.setPrefWidth(80);
            HBox.setHgrow(colDescricao, Priority.ALWAYS);
            colQuantidade.setPrefWidth(60);
            colPrecoUnitario.setPrefWidth(110);
            colSubtotal.setPrefWidth(110);
            colSubtotal.setAlignment(Pos.CENTER_RIGHT);

            linhaItem.getChildren().addAll(colCodigo, colDescricao, colQuantidade, colPrecoUnitario, colSubtotal);
            corpoItens.getChildren().add(linhaItem);
        }


        VBox rodape = new VBox(5);
        rodape.setAlignment(Pos.CENTER_RIGHT);
        rodape.setPadding(new Insets(10, 0, 0, 0));
        rodape.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #ccc;");

        Label totalBruto = new Label("Total Bruto: R$ " + nota.getPrecoBruto());
        Label descontoL = new Label("Desconto (%): " + nota.getDesconto() + "%");
        Label totalFinal = new Label("TOTAL FINAL: R$ " + nota.getPrecoFinal());
        totalFinal.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

        rodape.getChildren().addAll(totalBruto, descontoL, totalFinal);

        layoutNota.getChildren().addAll(cabecalho, cabecalhoItens, corpoItens, new Separator(), rodape);

        Button btnAcao = new Button("Confirmar Impressão / Gerar PDF");
        btnAcao.setStyle("-fx-font-size: 12pt; -fx-padding: 8 15;");


        btnAcao.setOnAction(event -> {
            System.out.println("Ação de impressão para a Nota Nº " + nota.getNumeroNota() + " solicitada.");

        });


        HBox containerBotao = new HBox(btnAcao);
        containerBotao.setAlignment(Pos.CENTER);
        containerBotao.setPadding(new Insets(10, 0, 0, 0));


        VBox telaCompleta = new VBox(10, layoutNota, containerBotao);
        telaCompleta.setPadding(new Insets(10));


        Stage palcoPopup = new Stage();
        palcoPopup.initModality(Modality.APPLICATION_MODAL);
        palcoPopup.setTitle("Pré-visualização da Nota de Venda - Nº " + nota.getNumeroNota());


        Scene cena = new Scene(telaCompleta, 800, 800);
        palcoPopup.setScene(cena);
        palcoPopup.show();
    }

}
