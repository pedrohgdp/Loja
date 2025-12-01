package com.example.estoque;

import com.example.estoque.service.SingletonItemPreencherLista;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private SingletonItemPreencherLista preencherListaInstance = SingletonItemPreencherLista.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        preencherListaInstance.preencherItensViaDB();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/estoque/fxml/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 700);
        stage.setTitle("Loja!");
        stage.setScene(scene);
        stage.show();
    }
}