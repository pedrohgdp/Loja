package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.service.ReturnDiscount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    ReturnDiscount discountController = new ReturnDiscount();
    Item itemReturned;
    ObservableList<Item> itemList = FXCollections.observableArrayList();
    ListController listController;
    AmountItemController amountItemController;
    Integer amountItem;

    //FXML variables
    @FXML
    Label noteNumber;
    @FXML
    Label client;
    @FXML
    CheckBox takeClient;
    @FXML
    TableView<Item> itens;
    @FXML
    TableColumn<Item, String> code;
    @FXML
    TableColumn<Item, String> description;
    @FXML
    TableColumn<Item, Double> soloPrice;
    @FXML
    TableColumn<Item, Integer> amount;
    @FXML
    TableColumn<Item, Double> totalPrice;
    @FXML
    TableColumn<Item, String> brand;
    @FXML
    Label priceLabel;
    @FXML
    Label discountTextArea;
    @FXML
    TextField discountTextField;
    @FXML
    Label finalPriceLabel;
    @FXML
    Button printNote;
    @FXML
    Button addItem;
    @FXML
    Button removeItem;





    @FXML
    public void setOnRemoveItemButton(){
        removeItem();
    }

    @FXML
    public void setOnAddItemButton(){
        addItem();
    }

    public void removeItem(){
        Item item = itens.getSelectionModel().getSelectedItem();
        if(item.getAmountSold() == 1){
            itens.getItems().remove(item);
        }else{
            loadAmountItem();
            if(amountItem < item.getAmountSold()){
                item.setAmountSold(item.getAmountSold() - amountItem);
            }else{
                itens.getItems().remove(item);
            }
        }
        itens.refresh();
        updatePrice();
    }

    public void addItem(){
        try{
            loadListStage();

            itemReturned = listController.ProcessItem();
            if(itemReturned == null) return;

            //Stream para ver se o item existe no array list
            Optional<Item> itemExist = itemList.stream()
                    .filter(i -> i.getDescription().equals(itemReturned.getDescription()))
                    .findFirst();

            if(itemExist.isPresent()){
                Item findItem = itemExist.get();
                findItem.setAmountSold(findItem.getAmountSold() + amountItem);
            }else{
                itemReturned.setAmountSold(amountItem);
                itemList.add(itemReturned);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        fillTable(itemList);
        itens.refresh();
        updatePrice();
    }

    public void loadAmountItem(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/AmountItem.fxml"));
            Parent root = fxmlLoader.load();
            amountItemController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Quantidade");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            amountItem = amountItemController.returnAmount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadListStage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListItens.fxml"));
            Parent root = fxmlLoader.load();
            listController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Lista Item");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadAmountItem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillTable(ObservableList<Item> itemList){
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        soloPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amountSold"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        itens.setItems(itemList);
    }

    public void updatePrice(){
        Double price = 0.0;

        for(Item items : itemList){
            price += items.getTotal();
        }

        String priceString = price.toString();
        priceLabel.setText("Valor: " + priceString);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePrice();

        //ADICIONA UM LISTENER NO DESCONTO E QUE TEM UMA TRAVA
        //ELA COMECA FALSE, MUDA PARA TRUE, FAZ A ATUALIZACAO DO VALOR FINAL ( OU DESCONTO )
        //BOTA A TRAVA PARA FALSE DNV

        // A MÁGICA: Preço Final = Subtotal * (1 - (Percentual / 100))
        //A MÁGICA: Percentual = (1 - (Preço Final / Subtotal)) * 100

        //Isso se subtotal for maior que 0

        //Shortcuts


    }
}
