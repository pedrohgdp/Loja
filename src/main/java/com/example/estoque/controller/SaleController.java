package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.model.Note;
import com.example.estoque.service.ItemsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    private Item itemReturned;
    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private ListController listController;
    private AmountItemController amountItemController;
    private PriceItemSellController priceItemSellController;
    private Integer amountItem;
    private double priceItem;
    private boolean discountPriceController = false;

    //FXML variables
    @FXML
    private Label noteNumber;
    @FXML
    private Label client;
    @FXML
    private Label solicitante;
    @FXML
    private CheckBox takeClient;
    @FXML
    private TableView<Item> itens;
    @FXML
    private TableColumn<Item, String> code;
    @FXML
    private TableColumn<Item, String> description;
    @FXML
    private TableColumn<Item, Double> soloPrice;
    @FXML
    private TableColumn<Item, Integer> amount;
    @FXML
    private TableColumn<Item, Double> totalPrice;
    @FXML
    private TableColumn<Item, String> brand;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField discountTextField;
    @FXML
    private TextField finalPriceTextField;
    @FXML
    private Button printNote;
    @FXML
    private Button addItem;
    @FXML
    private Button removeItem;
    @FXML
    private Label priceNumber;


    private void displayNote(Note note) throws IOException {
        VBox noteLayout = new VBox(10);
        noteLayout.setPadding(new Insets(20));
        noteLayout.setPrefWidth(560); // Ajuste interno para a largura 600
        noteLayout.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: white;");

        // --- Cabeçalho (Loja, Data) ---
        // COMENTÁRIO: ALTERE O NOME DA LOJA AQUI
        Label storeName = new Label("MINHA LOJA S.A.");
        storeName.setStyle("-fx-font-size: 18pt; -fx-font-weight: bold; -fx-alignment: center;");

        Label noteDateL = new Label("DATA: " + note.getNoteDate());
        noteDateL.setStyle("-fx-font-style: italic;");

        // 🎯 CORREÇÃO DOS RÓTULOS (USANDO APENAS O VALOR LIMPO)
        String noteNumValue = note.getNoteNumber() == null || note.getNoteNumber().trim().isEmpty() ? "" : note.getNoteNumber();
        String clientValue = note.getClient() == null || note.getClient().trim().isEmpty() ? "" : note.getClient();
        String solicitanteValue = note.getRequestingParty() == null || note.getRequestingParty().trim().isEmpty() ? "" : note.getRequestingParty();

        // Labels exibem o rótulo fixo + o valor (que agora não deve conter o rótulo)
        Label noteNum = new Label("NOTA Nº: " + noteNumValue);
        Label clientL = new Label("CLIENTE: " + clientValue);
        Label requestingPartyL = new Label("SOLICITANTE: " + solicitanteValue);

        VBox header = new VBox(5, storeName, noteDateL, new Separator(), noteNum, clientL, requestingPartyL, new Separator());

        // --- Cabeçalho da Tabela (Itens) ---
        HBox itemsHeader = new HBox(25); // Aumento do espaçamento entre as colunas
        Label colCodeH = new Label("CÓDIGO");
        Label colDescriptionH = new Label("DESCRIÇÃO");
        Label colAmountH = new Label("QTD");
        Label colUnitPriceH = new Label("PREÇO UNIT.");
        Label colSubtotalH = new Label("SUBTOTAL");

        // Larguras ajustadas
        colCodeH.setPrefWidth(80);
        HBox.setHgrow(colDescriptionH, Priority.ALWAYS);
        colAmountH.setPrefWidth(60);
        colUnitPriceH.setPrefWidth(120);
        colSubtotalH.setPrefWidth(120);

        // NOVO: Alinha o texto "SUBTOTAL" à direita
        colSubtotalH.setAlignment(Pos.CENTER_RIGHT);

        itemsHeader.getChildren().addAll(colCodeH, colDescriptionH, colAmountH, colUnitPriceH, colSubtotalH);
        itemsHeader.setStyle("-fx-font-weight: bold;");

        // --- Corpo (Itens) ---
        VBox itemsBody = new VBox(5); // Aumento do espaçamento vertical entre os itens
        for (Item item : note.getItems()) {
            HBox itemRow = new HBox(25); // Aumento do espaçamento horizontal
            Label colCode = new Label(item.getCode());
            Label colDescription = new Label(item.getDescription());
            Label colAmount = new Label(String.valueOf(item.getAmountSold()));
            Label colUnitPrice = new Label(String.format("R$ %.2f", item.getPrice()));
            Label colSubtotal = new Label(String.format("R$ %.2f", item.getTotal()));

            // Aplicando as larguras
            colCode.setPrefWidth(80);
            HBox.setHgrow(colDescription, Priority.ALWAYS);
            colAmount.setPrefWidth(60);
            colUnitPrice.setPrefWidth(110);
            colSubtotal.setPrefWidth(110);
            colSubtotal.setAlignment(Pos.CENTER_RIGHT);

            itemRow.getChildren().addAll(colCode, colDescription, colAmount, colUnitPrice, colSubtotal);
            itemsBody.getChildren().add(itemRow);
        }

        // --- Rodapé (Totais) ---
        VBox footer = new VBox(5);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(10, 0, 0, 0));
        footer.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #ccc;");

        Label grossTotal = new Label("Total Bruto: R$ " + note.getGrossPrice());
        Label discountL = new Label("Desconto (%): " + note.getDiscount() + "%");
        Label finalTotal = new Label("TOTAL FINAL: R$ " + note.getFinalPrice());
        finalTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

        footer.getChildren().addAll(grossTotal, discountL, finalTotal);

        noteLayout.getChildren().addAll(header, itemsHeader, itemsBody, new Separator(), footer);

        Button btnAction = new Button("Confirmar Impressão / Gerar PDF");
        btnAction.setStyle("-fx-font-size: 12pt; -fx-padding: 8 15;");

        // Action on button click
        btnAction.setOnAction(event -> {
            System.out.println("Ação de impressão para a Nota Nº " + note.getNoteNumber() + " solicitada.");
            // **PLACE YOUR FINAL PRINTING/PDF GENERATION LOGIC HERE**
        });


        HBox buttonContainer = new HBox(btnAction);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));


        VBox completeScreen = new VBox(10, noteLayout, buttonContainer);
        completeScreen.setPadding(new Insets(10));


        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Pré-visualização da Nota de Venda - Nº " + note.getNoteNumber());


        Scene scene = new Scene(completeScreen, 800, 800);
        popupStage.setScene(scene);
        popupStage.show();
    }

    @FXML
    public void setOnPrintNote() {
        Note note = new Note(
                noteNumber.getText(),
                client.getText(),
                solicitante.getText(),
                FXCollections.observableArrayList(itens.getItems()),
                priceNumber.getText(), // Gross Value
                discountTextField.getText().isEmpty() ? "0.00" : discountTextField.getText(),
                finalPriceTextField.getText().isEmpty() ? priceNumber.getText() : finalPriceTextField.getText()
        );
        try {
            displayNote(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                itemReturned.setPrice(priceItem);
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
            loadPriceItem();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPriceItem(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/priceItem.fxml"));
            Parent root = fxmlLoader.load();
            priceItemSellController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Price");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            priceItem = priceItemSellController.returnPrice();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadListStage(){
        ItemsService itemsService = ItemsService.getInstance();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListItens.fxml"));
            Parent root = fxmlLoader.load();
            listController = fxmlLoader.getController();
            listController.setItemsOnListItems(itemsService.getItems());
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
        priceNumber.setText(priceString);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePrice();

        discountTextField.textProperty().addListener((obs, oldText, newText) -> {
            try{
                if(discountPriceController) return;

                if(newText == null || newText.trim().isEmpty()){
                    discountTextField.clear();
                    finalPriceTextField.clear();
                    return;
                }
                double price = Double.parseDouble(priceNumber.getText());
                double discount = Double.parseDouble(discountTextField.getText().replace(',', '.'));
                double finalPrice = price * (1 - discount / 100);

                //Bloqueia a atualizacao quando for atualizar
                //E desbloqueia
                //Isso e um flag ( trava ) muito usado
                discountPriceController = true;
                finalPriceTextField.setText(String.format("%.2f", finalPrice));
                discountPriceController = false;
                //BOtar if para ver se a porcentagem e maior que 20% e dar erro
            } catch (NumberFormatException e) {
                discountTextField.clear();
                finalPriceTextField.clear();
            }
        });

        finalPriceTextField.textProperty().addListener((obs, oldText, newText) ->{
            try {

                if(discountPriceController) return;

                if (newText == null || newText.trim().isEmpty()) {
                    finalPriceTextField.clear();
                    discountTextField.clear();
                    return;
                }

                double price = Double.parseDouble(priceNumber.getText());
                double finalPrice = Double.parseDouble(finalPriceTextField.getText().replace(',', '.'));


                // evita divisão por zero
                if (price == 0) {
                    discountTextField.clear();
                    return;
                }

                double discount = (1 - (finalPrice / price)) * 100;
                discountPriceController = true;
                discountTextField.setText(String.format("%.2f", discount));
                discountPriceController = false;
            } catch (NumberFormatException e) {
                discountTextField.clear();
                finalPriceTextField.clear();
            }
        });


    }
}
