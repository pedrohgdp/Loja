package com.example.estoque.controller;

import com.example.estoque.model.Item;
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
    private TableColumn<Item, Integer> ShelfLevel;
    @FXML
    private TextField SearchBar;


    private String typedText;
    private List<String> StringText;


    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private FilteredList<Item> filteredListItem;

    private Item filtroOleoFire = new Item("psl55", "Filtro oleo Fire", 19.99, 20, "Tecfil", 212, "i22");
    private Item filtroOleoCorsa = new Item("psl619", "Filtro oleo celta", 19.99, 20, "Tecfil", 213, "j12");
    private Item mangueiraSupFIre = new Item("21530.0", "mangueira sup Fire", 19.99, 20, "Tecfil", 107, "j10");
    private Item mangueiraInfFire = new Item("1234231.0", "mangueira inf Fire", 19.99, 20, "Tecfil", 110, "a6");
    private Item casquilhoFixoFire = new Item("12312", "casquilho fixo fire 0,25", 19.99, 20, "Tecfil", 99, "i1");
    private Item a = new Item("psl55", "mangueira oleo celta", 19.99, 20, "Tecfil", 99, "i1");
    private Item b = new Item("psl55", "mangueira inf celta", 19.99, 20, "Tecfil", 99, "i1");
    private Item c = new Item("psl55", "mangueira sup Celta", 19.99, 20, "Tecfil", 99, "i1");
    private Item d = new Item("psl55", "arranque Onix", 19.99, 20, "Tecfil", 99, "i1");
    private Item e = new Item("psl55", "casquilho biela Onix", 19.99, 20, "Tecfil", 99, "i1");
    private Item f = new Item("psl55", "Filtro oleo Onix", 19.99, 20, "Tecfil", 99, "i1");
    private Item g = new Item("psl55", "mang oleo cobalt", 19.99, 20, "Tecfil", 99, "i1");
    private Item h = new Item("psl55", "mangueira oleo cobalt", 19.99, 20, "Tecfil", 99, "i1");
    private Item i = new Item("psl55", "Filtro oleo cobalt", 19.99, 20, "Tecfil", 99, "i1");
    private Item j = new Item("psl55", "casquilho fixo Cobalt 0,50", 19.99, 20, "Tecfil", 99, "i1");
    private Item k = new Item("psl55", "arranque Fire", 19.99, 20, "Tecfil", 99, "i1");
    private Item m = new Item("psl55", "Filtro oleo Uno", 19.99, 20, "Tecfil", 99, "i1");
    private Item n = new Item("psl55", "peneira oleo Fusca", 19.99, 20, "Tecfil", 99, "i1");
    private Item o = new Item("psl55", "junta peneira oleo Fusca", 19.99, 20, "Tecfil", 99, "i1");

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


    public Item ProcessItem(Item item){
        System.out.println(item);
        return item;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableItens.getSelectionModel().setCellSelectionEnabled(true); ///Libera a selecao de celula nao so linha

        itemList.add(filtroOleoFire);
        itemList.add(filtroOleoCorsa);
        itemList.add(mangueiraSupFIre);
        itemList.add(mangueiraInfFire);
        itemList.add(casquilhoFixoFire);
        itemList.add(a);
        itemList.add(b);
        itemList.add(c);
        itemList.add(d);
        itemList.add(e);
        itemList.add(f);
        itemList.add(g);
        itemList.add(h);
        itemList.add(i);
        itemList.add(j);
        itemList.add(k);
        itemList.add(m);
        itemList.add(n);
        itemList.add(o);

        //Botamos nossa ObservableList em uma FilteredList para filtrar
        //E depois ela em uma sorted List e linkamos o sort da list com sort que a table faz
        filteredListItem = new FilteredList<>(itemList);
        SortedList<Item> sortedListItem = new SortedList(filteredListItem);
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
                Item selectItem = TableItens.getSelectionModel().getSelectedItem();
                if(selectItem != null){
                    ProcessItem(selectItem);
                }
            }
        });
    }

}
