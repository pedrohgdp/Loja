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

    private int codigoUltimo = 0; // Vai pegar do database

    private final AddCliente ADD_CLIENTE = new AddCliente();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirmarButton.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                if(adicionarClienteFuncao()){
                    fecharStage();
                }
            }
        });

    }

    public boolean checarSeNaoFoiPostoValor() {
        return !nome.getText().isEmpty()
                && !limite.getText().isEmpty()
                && !cpfCnpj.getText().isEmpty();
    }

    public boolean adicionarClienteFuncao(){
        Cliente novoCliente = new Cliente();
        if(checarSeNaoFoiPostoValor()){
            try{
                novoCliente.setNome(nome.getText());
                novoCliente.setLimiteTotal(Double.parseDouble(limite.getText()));
                novoCliente.setCpfCnpj(cpfCnpj.getText());
                return ADD_CLIENTE.addCliente(novoCliente);
            } catch (NumberFormatException e) {
                mostrarErro("Valor inválido. Verifique e tente novamente.");
                return false;
            }
        }else{
            mostrarErro("Valor não colocado.");
            return false;
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void fecharStage(){
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.close();
    }
}
