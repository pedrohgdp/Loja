package com.example.estoque.controller.ControleAdicao;

import com.example.estoque.model.Cliente;
import com.example.estoque.repository.AddCliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddClienteController implements Initializable {

    @FXML
    private TextField nome;
    @FXML
    private TextField limite;
    @FXML
    private TextField cpfCnpj;
    @FXML
    private Button confirmarButton;

    private Cliente novoCliente = new Cliente();
    private int codigoUltimo = 0;

    private final AddCliente ADD_CLIENTE = new AddCliente();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirmarButton.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                adicionarClienteFuncao();
                fecharStage();
            }
        });

    }

    public boolean checarSeNaoFoiPostoValor() {
        return !nome.getText().isEmpty()
                && !limite.getText().isEmpty()
                && !cpfCnpj.getText().isEmpty();
    }

    public void adicionarClienteFuncao(){
        if(checarSeNaoFoiPostoValor()){
            try{
                novoCliente.setCodigo(codigoUltimo);
                novoCliente.setNome(nome.getText());
                novoCliente.setLimiteTotal(Double.parseDouble(limite.getText()));
                novoCliente.setCpfCnpj(cpfCnpj.getText());
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valor inválido");
                alert.setContentText("Valor posto inválido. Verifique e tente novamente.");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Não posto valor");
            alert.setContentText("Valor não colocado.");
            alert.showAndWait();
        }

        //chamar add cliente

    }

    public void fecharStage(){
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.close();
    }
}
