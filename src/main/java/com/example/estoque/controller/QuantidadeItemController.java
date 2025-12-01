package com.example.estoque.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class QuantidadeItemController implements Initializable {
    @FXML
    TextField quantidadeTextField;
    @FXML
    Button confirmarButton;

    private Integer amount = 1;

    public int retornarQuantidade(){
        return amount;
    }

    public void pegarNumeroDoTextView(){
        if(quantidadeTextField.getText().matches("\\d+")){
            amount = Integer.parseInt(quantidadeTextField.getText());
            confirmarButton.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Digite um número, texto não é aceitado.");
            alert.showAndWait();
        }
    }

    public void setOnConfirmarButton(){
        pegarNumeroDoTextView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantidadeTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                pegarNumeroDoTextView();
            }
        });
    }
}
