package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.repository.AddNewItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable{

    @FXML
    private TextField code;
    @FXML
    private TextField description;
    @FXML
    private TextField price;
    @FXML
    private TextField amount;
    @FXML
    private TextField brand;
    @FXML
    private TextField shelf;
    @FXML
    private TextField shelfLevel;
    @FXML
    private Button confirmButton;

    private Item newItem;

    private AddNewItem addNewItemDB = new AddNewItem();

    public boolean checkIfDontPutValue() {
        return !code.getText().isEmpty()
                && !description.getText().isEmpty()
                && !price.getText().isEmpty()
                && !amount.getText().isEmpty()
                && !brand.getText().isEmpty()
                && !shelf.getText().isEmpty()
                && !shelfLevel.getText().isEmpty();
    }

    public void addItemFunction(){
        if(checkIfDontPutValue()){
            try{
               newItem.setCode(code.getText());
               newItem.setDescription(description.getText());
               newItem.setPrice(Double.parseDouble(price.getText()));
               newItem.setAmount(Integer.parseInt(amount.getText()));
               newItem.setBrand(brand.getText());
               newItem.setShelf(Integer.parseInt(shelf.getText()));
               newItem.setShelfLevel(shelfLevel.getText());
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valor inválido");
                alert.setContentText("Valor posto inválido. Verifique e tente novamente.");
                alert.showAndWait();
                return;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Não posto valor");
            alert.setContentText("Valor não colocado.");
            alert.showAndWait();
            return;
        }

        addNewItemDB.addNewItemOnDB(newItem);
        System.out.println("adicionado com sucesso");
    }

    public void closeStage(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
    public void setOnConfirmButton(){
        addItemFunction();
        closeStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newItem = new Item();

        confirmButton.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addItemFunction();
                closeStage();
            }
        });

    }
}
