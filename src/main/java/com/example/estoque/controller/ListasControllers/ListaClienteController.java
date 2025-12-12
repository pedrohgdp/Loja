package com.example.estoque.controller.ListasControllers;


import com.example.estoque.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ListaClienteController implements Initializable {

    @FXML
    private TableView<Cliente> TabelaClientes;
    @FXML
    private TableColumn<Cliente, Integer> codigo;
    @FXML
    private TableColumn<Cliente, String> nome;
    @FXML
    private TableColumn<Cliente, Double> limiteUsado;
    @FXML
    private TableColumn<Cliente, Double> limiteTotal;
    @FXML
    private TableColumn<Cliente, String> cpfCnpj;
    @FXML
    private TextField barraPesquisa;


    private String textoDigitado;
    private List<String> listaTexto;
    private Cliente clienteSelecionado;

    private ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
    private FilteredList<Cliente> listaClienteFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TabelaClientes.getSelectionModel().setCellSelectionEnabled(true); //Libera a selecao de celula nao so linha

        //Botamos nossa ObservableList em uma FilteredList para filtrar
        //E depois ela em uma sorted List e linkamos o sort da list com sort que a table faz
        listaClienteFiltrada = new FilteredList<>(listaCliente);
        SortedList<Cliente> listaClienteSorted = new SortedList<>(listaClienteFiltrada);
        listaClienteSorted.comparatorProperty().bind(TabelaClientes.comparatorProperty()); //Pega a propriedade do Table view e
        //Linka com o sortedList para o sortedList meio que ordenar com base em como a tableview ordena

        preencherTabela(listaClienteSorted);


        barraPesquisa.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> barraPesquisa.clear());

        //Pesquisa
        barraPesquisa.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ENTER){
                textoDigitado = barraPesquisa.getText();
                System.out.println(textoDigitado);
                listaTexto = preencherArrayDeBusca(textoDigitado);

                listaClienteFiltrada.setPredicate(cliente -> {
                    if(listaTexto.isEmpty()){
                        return true;
                    }

                    String clienteNome = cliente.getNome().toLowerCase();

                    for(String key : listaTexto){
                        if(!clienteNome.contains(key)){
                            return false;
                        }
                    }
                    return true;
                });
            }
        });


        //Retornar o item selecionado, seja para venda ou para tela de ver preço.
        TabelaClientes.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                clienteSelecionado = TabelaClientes.getSelectionModel().getSelectedItem();
                if(clienteSelecionado != null){
                    TabelaClientes.getScene().getWindow().hide();
                }
            }
        });
    }

    public void preencherTabela(SortedList<Cliente> listaCliente){
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        limiteUsado.setCellValueFactory(new PropertyValueFactory<>("limiteUsado"));
        limiteTotal.setCellValueFactory(new PropertyValueFactory<>("limiteTotal"));
        cpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));


        TabelaClientes.setItems(listaCliente);
    }

    public List<String> preencherArrayDeBusca(String textoDigitado){
        if(textoDigitado.equals(" ") || textoDigitado.isEmpty()) return new ArrayList<>();
        List<String> listaTexto;
        String textCleanWPorcent = textoDigitado.toLowerCase().trim();

        if(textCleanWPorcent.contains("%")){
            textCleanWPorcent = textCleanWPorcent.replace("%", "");
        }

        listaTexto = new ArrayList<>(Arrays.asList(textCleanWPorcent.split("\\s+")));
        // \\s+ divide por espaco, tab, quebra de linha qualquer coisa

        return listaTexto;
    }

    public Cliente processarCliente(){
        System.out.println(clienteSelecionado);
        return clienteSelecionado;
    }


}
