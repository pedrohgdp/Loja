package com.example.estoque;

import com.example.estoque.controller.ListaItemController;
import com.example.estoque.service.SingletonPreencherLista;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private final ListaItemController LISTA_ITEM_CONTROLLER = new ListaItemController();
    private final SingletonPreencherLista SINGLETON_PREENCHER_LISTA = SingletonPreencherLista.getInstance();


    @Override
    public void start(Stage stage) throws IOException {
        SINGLETON_PREENCHER_LISTA.preencherItensViaDB();
        LISTA_ITEM_CONTROLLER.setListaItem(SINGLETON_PREENCHER_LISTA.getItens());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/estoque/fxml/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1081, 701);
        stage.setTitle("Loja!");
        stage.setScene(scene);
        stage.show();
    }
}