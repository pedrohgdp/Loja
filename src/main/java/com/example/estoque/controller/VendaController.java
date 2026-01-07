package com.example.estoque.controller;

import com.example.estoque.controller.ListasControllers.ListaItemController;
import com.example.estoque.model.Item;
import com.example.estoque.model.Nota;
import com.example.estoque.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class VendaController implements Initializable {

    private Venda venda = new Venda();
    private Item itemRetornado;
    private Integer quantidadeItem;
    private double precoItem;
    private boolean isUpdating = false;

    @FXML private Label numeroNota, cliente, solicitante, preco;
    @FXML private TableView<Item> itens;
    @FXML private TableColumn<Item, String> codigo, descricao, marca;
    @FXML private TableColumn<Item, Double> precoUnitario, precoTotal;
    @FXML private TableColumn<Item, Integer> quantidade;
    @FXML private TextField descontoTextField, precoFinalTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabela();
        configurarListeners();
    }

    private void configurarTabela() {
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        precoUnitario.setCellValueFactory(new PropertyValueFactory<>("preco"));
        quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeVenda"));
        marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        precoTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        itens.setItems(venda.getListaItens());
    }

    private void configurarListeners() {
        // Listener do Desconto
        descontoTextField.textProperty().addListener((obs, old, newValue) -> {
            if (isUpdating) return;

            // Se o usuário apagar tudo, resetamos o desconto para 0 na lógica
            if (newValue.isEmpty()) {
                venda.setDescontoPercentual(0);
                atualizarInterfaceFinanceira(true, false);
                return;
            }

            if (newValue.endsWith(",") || newValue.endsWith(".")) return;

            try {
                double d = Double.parseDouble(newValue.replace(',', '.'));
                if (d > 20.0) {
                    mostrarErro("O desconto não pode ser superior a 20%.");
                    d = 20.0;
                    venda.setDescontoPercentual(d);
                    atualizarInterfaceFinanceira(true, true);
                } else {
                    venda.setDescontoPercentual(d);
                    atualizarInterfaceFinanceira(true, false);
                }
            } catch (NumberFormatException e) { }
        });

        // Listener do Preço Final
        precoFinalTextField.textProperty().addListener((obs, old, newValue) -> {
            if (isUpdating) return;

            if (newValue.isEmpty()) {
                venda.setDescontoPercentual(0);
                atualizarInterfaceFinanceira(false, true);
                return;
            }

            if (newValue.endsWith(",") || newValue.endsWith(".")) return;

            try {
                double pFinal = Double.parseDouble(newValue.replace(',', '.'));
                double bruto = venda.getPrecoBruto();

                if (pFinal <= 0) return;

                double d = venda.calcularDescontoPorPrecoFinal(pFinal);

                if (d > 20.0) {
                    if (d < 80.0) {
                        mostrarErro("Desconto máximo de 20% atingido.");
                    }
                    venda.setDescontoPercentual(20.0);
                    atualizarInterfaceFinanceira(true, true);
                } else {
                    venda.setDescontoPercentual(Math.max(0, d));
                    atualizarInterfaceFinanceira(false, true);
                }
            } catch (NumberFormatException e) { }
        });
    }

    private void atualizarInterfaceFinanceira(boolean atualizarPrecoFinal, boolean atualizarDesconto) {
        isUpdating = true;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", symbols);

        double bruto = venda.getPrecoBruto();
        double pFinal = venda.getPrecoFinal();
        double desc = venda.getDescontoPercentual();

        preco.setText(df.format(bruto));

        if (atualizarDesconto) {
            descontoTextField.setText(df.format(desc));
        }

        if (atualizarPrecoFinal) {
            precoFinalTextField.setText(df.format(pFinal));
        }

        isUpdating = false;
    }

    private void limparCamposDesconto() {
        isUpdating = true;
        descontoTextField.clear();
        precoFinalTextField.clear();
        isUpdating = false;
    }

    @FXML
    public void setOnAdicionarItemButton() {
        abrirJanelaSelecaoItem();
        if (itemRetornado != null) {
            venda.adicionarOuAtualizarItem(itemRetornado, quantidadeItem, Double.valueOf(precoItem));
            itens.refresh();
            atualizarInterfaceFinanceira(true, true);
            itemRetornado = null;
        }
    }

    @FXML
    public void setOnRemoverItemButton() {
        Item selecionado = itens.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (selecionado.getQuantidadeVenda() > 1) {
            abrirJanelaQuantidade();
        } else {
            quantidadeItem = (Integer) 1;
        }

        venda.removerItem(selecionado, quantidadeItem);
        itens.refresh();
        atualizarInterfaceFinanceira(true, true);
        quantidadeItem = null;
    }

    @FXML
    public void setOnImprimirNota() throws IOException {
        Nota nota = new Nota(
                numeroNota.getText(), cliente.getText(), solicitante.getText(),
                FXCollections.observableArrayList(venda.getListaItens()),
                preco.getText(),
                descontoTextField.getText().isEmpty() ? "0.00" : descontoTextField.getText(),
                precoFinalTextField.getText().isEmpty() ? preco.getText() : precoFinalTextField.getText()
        );
        //Salvar nota no DB Aqui
        new ImprimirNotaTelaController().mostrarNota(nota);
    }


    private void abrirJanelaSelecaoItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListaItens.fxml"));
            Parent root = loader.load();
            ListaItemController controller = loader.getController();
            showModal("Lista de Itens", root);
            itemRetornado = controller.processarItem();
            if (itemRetornado != null) {
                abrirJanelaQuantidade();
                abrirJanelaPreco();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void abrirJanelaQuantidade() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/QuantidadeItem.fxml"));
            Parent root = loader.load();
            QuantidadeItemController controller = loader.getController();
            showModal("Quantidade", root);
            quantidadeItem = (Integer) controller.retornarQuantidade();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void abrirJanelaPreco() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/PrecoItem.fxml"));
            Parent root = loader.load();
            PrecoItemNaVendaController controller = loader.getController();
            showModal("Preço", root);
            precoItem = controller.retornarPreco();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void showModal(String title, Parent root) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}