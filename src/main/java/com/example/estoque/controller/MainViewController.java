package com.example.estoque.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainViewController {






    @FXML
    public void setDoSaleButton(){
        openNewStage("Fazer Venda", "Sale");
    }

    @FXML
    public void setConsultPriceButton(){
        openNewStage("Consultar preço", "ItemInfos");
    }

    private void openNewStage(String StageTitle, String fxmlName){
        String fxmlLocale = "/com/example/estoque/fxml/" + fxmlName + ".fxml";
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocale));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(StageTitle);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
