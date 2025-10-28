package com.example.estoque.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AmountItemController implements Initializable {
    @FXML
    TextField amountTextField;
    @FXML
    Button confirmButton;

    private Integer amount = 1;

    public int returnAmount(){
        return amount;
    }

    public void takeNumberFromTextField(){
        if(amountTextField.getText().matches("\\d+")){
            amount = Integer.parseInt(amountTextField.getText());
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
        amountTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                takeNumberFromTextField();
            }
        });
    }
}
