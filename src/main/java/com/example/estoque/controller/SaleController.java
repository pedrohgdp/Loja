package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.service.ReturnDiscount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleController {
    ReturnDiscount discountController = new ReturnDiscount();
    Item itemReturned;
    ObservableList<Item> observableList = FXCollections.observableArrayList();

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
    Label price;
    @FXML
    Label discount;
    @FXML
    Label finalPrice;
    @FXML
    Button printNote;
    @FXML
    Button addItem;

    public void FillTable(ObservableList<Item> itemList){
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        soloPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amountSold"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        itens.setItems(itemList);
        itens.refresh();
    }

    @FXML
    public void setOnAddItemButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListItens.fxml"));
            Parent root = fxmlLoader.load();
            ListController listController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Lista Item");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            itemReturned = listController.ProcessItem();
            if(itemReturned == null) return;

            //Stream para ver se o item existe no array list
            Optional<Item> itemExist = observableList.stream()
                    .filter(i -> i.getCode().equals(itemReturned.getCode()))
                    .findFirst();

            if(itemExist.isPresent()){
                Item findItem = itemExist.get();
                findItem.setAmountSold(findItem.getAmountSold() + 1);
            }else{
                itemReturned.setAmountSold(1);
                observableList.add(itemReturned);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FillTable(observableList);
        itens.refresh();
    }


 }
