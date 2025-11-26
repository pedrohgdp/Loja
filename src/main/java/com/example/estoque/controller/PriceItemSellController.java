package com.example.estoque.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class PriceItemSellController implements Initializable {
    @FXML
    TextField priceTextField;
    @FXML
    Button confirmButton;

    private double price = 1;

    public double returnPrice(){
        return price;
    }

    public void takeNumberFromTextField(){
        if(priceTextField.getText().matches("\\d+")){ //se ele e numero o \\d
            price = Double.parseDouble(priceTextField.getText());
            confirmButton.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Digite um número, texto não é aceitado.");
            alert.showAndWait();
        }
    }


    public void setOnConfirmButton(){
        takeNumberFromTextField();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                takeNumberFromTextField();
            }
        });
    }
}
