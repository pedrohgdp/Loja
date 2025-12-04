package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.service.SingletonPreencherLista;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemInfosController implements Initializable {
    @FXML
    private Button abrirLista;
    @FXML
    private Label codigoLabel;
    @FXML
    private Label descricaoLabel;
    @FXML
    private Label precoLabel;
    @FXML
    private Label quantidadeLabel;
    @FXML
    private Label marcaLabel;
    @FXML
    private Label estanteLabel;
    @FXML
    private Label prateleiraLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        abrirLista.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.F3){
                Item item = abrirListaFuncao();
                setarLabels(item);
                System.out.println("Item na classe de info: " + item.toString());
            }
        });
    }

    @FXML
    public void setOnAbrirListaButton(){
        Item item = abrirListaFuncao();
        setarLabels(item);
        System.out.println("Item na classe de info: " + item.toString());
    }

    public Item abrirListaFuncao(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListaItens.fxml"));
            Parent root = fxmlLoader.load();
            ListaItemController listaItemController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Lista de Items");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return listaItemController.processarItem(); //Retorno o item que o controller da stage guarda
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setarLabels(Item item){
        codigoLabel.setText("Codigo: " + item.getCodigo());
        descricaoLabel.setText("Descricao: " + item.getDescricao());
        precoLabel.setText("Preco Unitario: " + item.getPreco());
        quantidadeLabel.setText("Quantidade Estoque: " + item.getQuantidade());
        marcaLabel.setText("Marca: " + item.getMarca());
        estanteLabel.setText("Estante: " + item.getEstante());
        prateleiraLabel.setText("Pratileira: " + item.getPrateleira());
    }
}
