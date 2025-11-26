package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.service.ItemsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemInfosController implements Initializable {
    @FXML
    private Button openList;
    @FXML
    private Label codeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label brandLabel;
    @FXML
    private Label shelfLabel;
    @FXML
    private Label shelfLevelLabel;

    public Item OpenList(){
        ItemsService itemsService = ItemsService.getInstance();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListItens.fxml"));
            Parent root = fxmlLoader.load();
            ListController listController = fxmlLoader.getController(); // Pego o controller que e o que vai guardar
            //A variavel do item corretamente
            listController.setItemsOnListItems(itemsService.getItems());
            Stage stage = new Stage();
            stage.setTitle("Lista de Items");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return listController.ProcessItem(); //Retorno o item que o controller da stage guarda
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setLabels(Item item){
        codeLabel.setText("Codigo: " + item.getCode());
        descriptionLabel.setText("Descricao: " + item.getDescription());
        priceLabel.setText("Preco Unitario: " + item.getPrice());
        amountLabel.setText("Quantidade Estoque: " + item.getAmount());
        brandLabel.setText("Marca: " + item.getBrand());
        shelfLabel.setText("Estante: " + item.getShelf());
        shelfLevelLabel.setText("Pratileira: " + item.getShelfLevel());
    }

    @FXML
    public void setOnOpenListButton(){
        Item item = OpenList();
        setLabels(item);
        System.out.println("Item na classe de info: " + item.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openList.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.F3){
                Item item = OpenList();
                setLabels(item);
                System.out.println("Item na classe de info: " + item.toString());
            }
        });
    }
}
