package com.example.estoque.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class PrecoItemNaVendaController implements Initializable {
    @FXML
    TextField precoTextField;
    @FXML
    Button confirmarButton;

    private double price = 1;

    public double retornarPreco(){
        return price;
    }

    public void pegarNumeroDoTextField(){
        if(precoTextField.getText().matches("\\d+")){ //se ele e numero o \\d
            price = Double.parseDouble(precoTextField.getText());
            confirmarButton.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Digite um número, texto não é aceitado.");
            alert.showAndWait();
        }
    }

    public void setOnConfirmarButton(){
        pegarNumeroDoTextField();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        precoTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                pegarNumeroDoTextField();
            }
        });
    }
}
