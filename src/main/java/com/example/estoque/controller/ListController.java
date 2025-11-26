package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.repository.ItemDatabaseLoader;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.*;

public class ListController implements Initializable {

    private static ListController listControllerInstance = null;

    @FXML
    private TableView<Item> TableItens;
    @FXML
    private TableColumn<Item, String> ItemCode;
    @FXML
    private TableColumn<Item, String> ItemDescription;
    @FXML
    private TableColumn<Item, Double> ItemPrice;
    @FXML
    private TableColumn<Item, Integer> ItemAmount;
    @FXML
    private TableColumn<Item, String> Brand;
    @FXML
    private TableColumn<Item, Integer> Shelf;
    @FXML
    private TableColumn<Item, String> ShelfLevel;
    @FXML
    private TextField SearchBar;

    private String typedText;
    private List<String> StringText;
    private Item selectItem;


    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private FilteredList<Item> filteredListItem;


    public void FillTable(SortedList<Item> itemList){
        ItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        ItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ItemAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        Shelf.setCellValueFactory(new PropertyValueFactory<>("shelf"));
        ShelfLevel.setCellValueFactory(new PropertyValueFactory<>("shelfLevel"));

        TableItens.setItems(itemList);
    }

    public List<String> fillArrayStringSearch(String typedText){
        if(typedText.equals(" ") || typedText.isEmpty()) return new ArrayList<>();
        List<String> listText;
        String textCleanWPorcent = typedText.toLowerCase().trim();

        if(textCleanWPorcent.contains("%")){
            textCleanWPorcent = textCleanWPorcent.replace("%", "");
        }

        listText = new ArrayList<>(Arrays.asList(textCleanWPorcent.split("\\s+")));
        // \\s+ divide por espaco, tab, quebra de linha qualquer coisa

        return listText;
    }

    public Item ProcessItem(){
        System.out.println(selectItem);
        return selectItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableItens.getSelectionModel().setCellSelectionEnabled(true); ///Libera a selecao de celula nao so linha

        //Botamos nossa ObservableList em uma FilteredList para filtrar
        //E depois ela em uma sorted List e linkamos o sort da list com sort que a table faz
        filteredListItem = new FilteredList<>(itemList);
        SortedList<Item> sortedListItem = new SortedList<>(filteredListItem);
        sortedListItem.comparatorProperty().bind(TableItens.comparatorProperty()); //Pega a propriedade do Table view e
        //Linka com o sortedList para o sortedList meio que ordenar com base em como a tableview ordena

        FillTable(sortedListItem);


        SearchBar.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> SearchBar.clear());

        //Pesquisa
        SearchBar.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ENTER){
                typedText = SearchBar.getText();
                System.out.println(typedText);
                StringText = fillArrayStringSearch(typedText);

                filteredListItem.setPredicate(item -> {
                    if(StringText.isEmpty()){
                        return true;
                    }

                    String itemDescription = item.getDescription().toLowerCase();

                    for(String key : StringText){
                        if(!itemDescription.contains(key)){
                            return false;
                        }
                    }
                    return true;
                });
            }
        });


        //Retornar o item selecionado, seja para venda ou para tela de ver preço.
        TableItens.setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.ENTER){
                selectItem = TableItens.getSelectionModel().getSelectedItem();
                if(selectItem != null){
                    TableItens.getScene().getWindow().hide();
                }
            }
        });
    }

    public static synchronized ListController getInstance(){
        if(listControllerInstance == null){
            listControllerInstance = new ListController();
        }

        return listControllerInstance;
    }

    public void setItemsOnListItems(List<Item> items){
        itemList.setAll(items);
    }
}
