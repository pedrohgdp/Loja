package com.example.estoque.controller.ControleAdicao;

import com.example.estoque.model.Item;
import com.example.estoque.repository.AddNovoItem;
import com.example.estoque.service.SingletonPreencherLista;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable{

    @FXML
    private TextField codigo;
    @FXML
    private TextField descricao;
    @FXML
    private TextField preco;
    @FXML
    private TextField quantidade;
    @FXML
    private TextField marca;
    @FXML
    private TextField estante;
    @FXML
    private TextField prateleira;
    @FXML
    private Button confirmarButton;

    private Item novoItem;

    private final AddNovoItem ADD_NOVO_ITEM = new AddNovoItem();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        novoItem = new Item();

        confirmarButton.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                adicionarItemFuncao();
                fecharStage();
            }
        });

    }

    public boolean checarSeNaoFoiPostoValor() {
        return !codigo.getText().isEmpty()
                && !descricao.getText().isEmpty()
                && !preco.getText().isEmpty()
                && !quantidade.getText().isEmpty()
                && !marca.getText().isEmpty()
                && !estante.getText().isEmpty()
                && !prateleira.getText().isEmpty();
    }

    public void adicionarItemFuncao(){
        if(checarSeNaoFoiPostoValor()){
            try{
               novoItem.setCodigo(codigo.getText());
               novoItem.setDescricao(descricao.getText());
               novoItem.setPreco(Double.parseDouble(preco.getText().replace(",", ".")));
               novoItem.setQuantidade(Integer.parseInt(quantidade.getText()));
               novoItem.setMarca(marca.getText());
               novoItem.setEstante(Integer.parseInt(estante.getText()));
               novoItem.setPrateleira(prateleira.getText());
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

        SingletonPreencherLista preencherLista = SingletonPreencherLista.getInstance();
        ADD_NOVO_ITEM.addNewItemOnDB(novoItem);
        preencherLista.preencherItensViaDB();
        System.out.println("adicionado com sucesso");
    }

    public void fecharStage(){
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.close();
    }

    public void setOnConfirmarButton(){
        adicionarItemFuncao();
        fecharStage();
    }

}
