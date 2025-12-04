package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.repository.AddNovoItem;
import com.example.estoque.service.SingletonPreencherLista;
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
import java.util.*;

public class ListaItemController implements Initializable {

    @FXML
    private TableView<Item> TabelaItens;
    @FXML
    private TableColumn<Item, String> codigoItem;
    @FXML
    private TableColumn<Item, String> descricaoItem;
    @FXML
    private TableColumn<Item, Double> precoItem;
    @FXML
    private TableColumn<Item, Integer> quantidadeItem;
    @FXML
    private TableColumn<Item, String> marca;
    @FXML
    private TableColumn<Item, Integer> estante;
    @FXML
    private TableColumn<Item, String> prateleira;
    @FXML
    private TextField barraPesquisa;

    private String textoDigitado;
    private List<String> listaTexto;
    private Item itemSelecionado;


    private ObservableList<Item> listaItem = FXCollections.observableArrayList();
    private FilteredList<Item> listaItemFiltrada;
    private final AddNovoItem ADD_ITEM = new AddNovoItem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SingletonPreencherLista singletonPreencherLista = SingletonPreencherLista.getInstance();
        listaItem.addAll(singletonPreencherLista.getItens());
        
        TabelaItens.getSelectionModel().setCellSelectionEnabled(true); //Libera a selecao de celula nao so linha

        //Botamos nossa ObservableList em uma FilteredList para filtrar
        //E depois ela em uma sorted List e linkamos o sort da list com sort que a table faz
        listaItemFiltrada = new FilteredList<>(listaItem);
        SortedList<Item> sortedListItem = new SortedList<>(listaItemFiltrada);
        sortedListItem.comparatorProperty().bind(TabelaItens.comparatorProperty()); //Pega a propriedade do Table view e
        //Linka com o sortedList para o sortedList meio que ordenar com base em como a tableview ordena

        preencherTabela(sortedListItem);


        barraPesquisa.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> barraPesquisa.clear());

        //Pesquisa
        barraPesquisa.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ENTER){
                textoDigitado = barraPesquisa.getText();
                System.out.println(textoDigitado);
                listaTexto = preencherArrayDeBusca(textoDigitado);

                listaItemFiltrada.setPredicate(item -> {
                    if(listaTexto.isEmpty()){
                        return true;
                    }

                    String itemDescription = item.getDescricao().toLowerCase();

                    for(String key : listaTexto){
                        if(!itemDescription.contains(key)){
                            return false;
                        }
                    }
                    return true;
                });
            }
        });


        //Retornar o item selecionado, seja para venda ou para tela de ver preço.
        TabelaItens.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                itemSelecionado = TabelaItens.getSelectionModel().getSelectedItem();
                if(itemSelecionado != null){
                    TabelaItens.getScene().getWindow().hide();
                }
            }
        });
    }

    public void setListaItem(ArrayList<Item> listaItemDoDB) {
        listaItem.setAll(listaItemDoDB);
    }

    public void preencherTabela(SortedList<Item> listaItem){
        codigoItem.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descricaoItem.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        precoItem.setCellValueFactory(new PropertyValueFactory<>("preco"));
        quantidadeItem.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        estante.setCellValueFactory(new PropertyValueFactory<>("estante"));
        prateleira.setCellValueFactory(new PropertyValueFactory<>("prateleira"));

        TabelaItens.setItems(listaItem);
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

    public Item processarItem(){
        System.out.println(itemSelecionado);
        return itemSelecionado;
    }

}
