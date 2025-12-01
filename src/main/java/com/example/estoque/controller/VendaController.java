package com.example.estoque.controller;

import com.example.estoque.model.Item;
import com.example.estoque.model.Nota;
import com.example.estoque.service.SingletonPreencherLista; // Mantido, nome de classe externa
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

public class VendaController implements Initializable {

    // Variáveis de controle
    private Item itemRetornado;
    private ObservableList<Item> listaItens = FXCollections.observableArrayList();
    private ListaItemController listaItemController;
    private QuantidadeItemController quantidadeItemController;
    private PrecoItemNaVendaController precoItemNaVendaController;
    private Integer quantidadeItem;
    private double precoItem;
    private boolean controladorDesconto = false;

    @FXML
    private Label numeroNota;
    @FXML
    private Label cliente;
    @FXML
    private Label solicitante;
    @FXML
    private CheckBox pegarCliente;
    @FXML
    private TableView<Item> itens;
    @FXML
    private TableColumn<Item, String> codigo;
    @FXML
    private TableColumn<Item, String> descricao;
    @FXML
    private TableColumn<Item, Double> precoUnitario;
    @FXML
    private TableColumn<Item, Integer> quantidade;
    @FXML
    private TableColumn<Item, Double> precoTotal;
    @FXML
    private TableColumn<Item, String> marca;
    @FXML
    private Label precoLabel;
    @FXML
    private TextField descontoTextField;
    @FXML
    private TextField precoFinalTextField;
    @FXML
    private Button imprimirNota;
    @FXML
    private Button adicionarItem;
    @FXML
    private Button removerItem;
    @FXML
    private Label preco;


    @FXML
    public void setOnImprimirNota() {
        //tem que pegar as variaveis certas de nota
        Nota nota = new Nota(
                numeroNota.getText(),
                cliente.getText(),
                solicitante.getText(),
                FXCollections.observableArrayList(itens.getItems()),
                preco.getText(),
                descontoTextField.getText().isEmpty() ? "0.00" : descontoTextField.getText(),
                precoFinalTextField.getText().isEmpty() ? preco.getText() : precoFinalTextField.getText()
        );
        //Tem que salvar a nota no db ( funcao de salvar )
        try {
            mostrarNota(nota);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setOnRemoverItemButton(){
        removerItemDaLista();
    }

    @FXML
    public void setOnAdicionarItemButton(){
        adicionarNovoItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizarPreco();

        descontoTextField.textProperty().addListener((obs, textoAntigo, textoNovo) -> { // Traduzido: oldText, newText -> textoAntigo, textoNovo
            try{
                if(controladorDesconto) return;

                if(textoNovo == null || textoNovo.trim().isEmpty()){
                    descontoTextField.clear();
                    precoFinalTextField.clear();
                    return;
                }
                double precoBruto = Double.parseDouble(preco.getText().replace(',', '.')); // Garantir ponto como separador
                double desconto = Double.parseDouble(descontoTextField.getText().replace(',', '.'));

                if (desconto > 20.0) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro de Desconto");
                    alerta.setHeaderText(null);
                    alerta.setContentText("O desconto não pode ser superior a 20%.");
                    alerta.showAndWait();
                    descontoTextField.setText("20.00"); // Define o máximo permitido
                    desconto = 20.0;
                }

                double precoFinal = precoBruto * (1 - desconto / 100); // Renomeado: finalPrice -> precoFinal

                // Bloqueia a atualizacao quando for atualizar
                // E desbloqueia
                // Isso é um flag (trava) muito usado
                controladorDesconto = true;
                precoFinalTextField.setText(String.format("%.2f", precoFinal).replace(',', '.')); // Garante ponto
                controladorDesconto = false;
            } catch (NumberFormatException e) {
                descontoTextField.clear();
                precoFinalTextField.clear();
            }
        });

        precoFinalTextField.textProperty().addListener((obs, textoAntigo, textoNovo) ->{ // Traduzido: oldText, newText -> textoAntigo, textoNovo
            try {

                if(controladorDesconto) return;

                if (textoNovo == null || textoNovo.trim().isEmpty()) {
                    precoFinalTextField.clear();
                    descontoTextField.clear();
                    return;
                }

                double precoBruto = Double.parseDouble(preco.getText().replace(',', '.')); // Renomeado: price -> precoBruto, Garante ponto
                double precoFinal = Double.parseDouble(precoFinalTextField.getText().replace(',', '.')); // Renomeado: finalPrice -> precoFinal, Garante ponto


                // Evita divisão por zero
                if (precoBruto == 0) {
                    descontoTextField.setText("0.00");
                    return;
                }

                // Evita desconto negativo (se o preço final for maior que o preço bruto)
                if (precoFinal > precoBruto) {
                    precoFinalTextField.setText(String.format("%.2f", precoBruto).replace(',', '.'));
                    precoFinal = precoBruto;
                }


                double desconto = (1 - (precoFinal / precoBruto)) * 100; // Renomeado: discount -> desconto

                // Validação de desconto acima de 20% (caso o usuário digite o valor final)
                if (desconto > 20.0) {
                    // Ajusta o desconto para o máximo e recalcula o preço final
                    desconto = 20.0;
                    precoFinal = precoBruto * (1 - desconto / 100);

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro de Desconto");
                    alerta.setHeaderText(null);
                    alerta.setContentText("O desconto não pode ser superior a 20%. O valor final foi ajustado para refletir um desconto máximo de 20%.");
                    alerta.showAndWait();

                    controladorDesconto = true;
                    precoFinalTextField.setText(String.format("%.2f", precoFinal).replace(',', '.'));
                    controladorDesconto = false;
                }

                controladorDesconto = true;
                descontoTextField.setText(String.format("%.2f", desconto).replace(',', '.'));
                controladorDesconto = false;
            } catch (NumberFormatException e) {
                descontoTextField.clear();
                precoFinalTextField.clear();
            }
        });
    }

    public void removerItemDaLista(){
        Item item = itens.getSelectionModel().getSelectedItem();
        if(item == null) return;


        if(item.getQuantidadeVenda() == 1){
            itens.getItems().remove(item);
        }else{
            carregarQuantidadeItem();


            if(quantidadeItem != null && quantidadeItem > 0 && quantidadeItem < item.getQuantidadeVenda()){
                item.setQuantidadeVenda(item.getQuantidadeVenda() - quantidadeItem);
            } else if (quantidadeItem != null && quantidadeItem >= item.getQuantidadeVenda()) {
                itens.getItems().remove(item);
            }
        }

        quantidadeItem = null;

        itens.refresh();
        atualizarPreco();
    }

    public void adicionarNovoItem(){
        try{
            carregarListaStage();


            if(itemRetornado == null) return;

            // Stream para ver se o item já existe no array list (baseado na descrição)
            Optional<Item> itemExiste = listaItens.stream()
                    .filter(i -> i.getDescricao().equals(itemRetornado.getDescricao()))
                    .findFirst();

            if(itemExiste.isPresent()){
                Item itemEncontrado = itemExiste.get();
                itemEncontrado.setQuantidadeVenda(itemEncontrado.getQuantidadeVenda() + quantidadeItem);
            }else{
                itemRetornado.setQuantidadeVenda(quantidadeItem);
                itemRetornado.setPreco(precoItem);
                listaItens.add(itemRetornado);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            itemRetornado = null;
            quantidadeItem = null;
        }


        preencherTabela(listaItens);
        itens.refresh();
        atualizarPreco();
    }

    public void carregarQuantidadeItem(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/QuantidadeItem.fxml"));
            Parent raiz = fxmlLoader.load();
            quantidadeItemController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Quantidade");
            stage.setScene(new Scene(raiz));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Depois que a janela é fechada, obtemos a quantidade
            quantidadeItem = quantidadeItemController.retornarQuantidade();

            // Se a quantidade for válida, chamamos a próxima etapa
            if(quantidadeItem != null && quantidadeItem > 0) {
                carregarPrecoItem(); // Renomeado: loadPriceItem -> carregarPrecoItem
            } else {
                // Define itemRetornado como null se a quantidade não for válida (cancelamento, 0, etc.)
                itemRetornado = null;
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a tela de Quantidade do Item", e);
        }
    }

    public void carregarPrecoItem(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/PrecoItem.fxml"));
            Parent raiz = fxmlLoader.load();
            precoItemNaVendaController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Preço");
            stage.setScene(new Scene(raiz));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            precoItem = precoItemNaVendaController.retornarPreco();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a tela de Preço do Item", e);
        }
    }

    public void carregarListaStage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/estoque/fxml/ListaItens.fxml"));
            Parent raiz = fxmlLoader.load();
            Stage estagio = new Stage();
            estagio.setTitle("Lista de Itens");
            estagio.setScene(new Scene(raiz));
            estagio.initModality(Modality.APPLICATION_MODAL); // Adicionado para bloquear a tela principal
            estagio.showAndWait();

            itemRetornado = listaItemController.processarItem();


            if (itemRetornado != null) {

            } else {
                itemRetornado = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar a tela de Lista de Itens", e);
        }
    }

    public void preencherTabela(ObservableList<Item> listaDeItens){
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo")); // Assumindo propriedade 'codigo' no Item
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao")); // Assumindo propriedade 'descricao' no Item
        precoUnitario.setCellValueFactory(new PropertyValueFactory<>("preco")); // Assumindo propriedade 'preco' no Item
        quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeVenda")); // Alterado de "amountSold" para "quantidadeVenda" (se o getter for getQuantidadeVenda())
        marca.setCellValueFactory(new PropertyValueFactory<>("marca")); // Assumindo propriedade 'marca' no Item
        precoTotal.setCellValueFactory(new PropertyValueFactory<>("total")); // Assumindo propriedade 'total' no Item

        itens.setItems(listaDeItens);
    }

    public void atualizarPreco(){ // Renomeado: updatePrice -> atualizarPreco
        Double precoBruto = 0.0; // Renomeado: price -> precoBruto

        for(Item item : listaItens){ // Renomeado: items -> item
            precoBruto += item.getTotal();
        }

        String precoString = String.format("%.2f", precoBruto); // Formatado para 2 casas decimais
        preco.setText(precoString.replace(',', '.')); // Garante que o separador decimal seja o ponto

        // Se o valor do campo de desconto estiver preenchido, recalcula o preço final
        if (!descontoTextField.getText().trim().isEmpty()) {
            // Chama o listener do desconto para atualizar o preço final
            // (Simula uma mudança no texto do desconto para recalcular o final)
            // Se for preciso, chame a lógica de cálculo diretamente aqui.
            // Para simplificar, vou manter a lógica de listener, mas isso pode ser perigoso
            // devido ao controladorDesconto.
            // Uma função de cálculo dedicada seria melhor.

            // Lógica de recálculo simplificada:
            if (!controladorDesconto) { // Evita loop infinito
                try {
                    double desconto = Double.parseDouble(descontoTextField.getText().replace(',', '.'));
                    double precoFinal = precoBruto * (1 - desconto / 100);
                    controladorDesconto = true;
                    precoFinalTextField.setText(String.format("%.2f", precoFinal).replace(',', '.'));
                    controladorDesconto = false;
                } catch (NumberFormatException e) {
                    // Ignora, significa que o desconto não está em formato numérico
                }
            }
        } else {
            // Se não há desconto, o preço final é o preço bruto
            controladorDesconto = true;
            precoFinalTextField.setText(precoString.replace(',', '.'));
            controladorDesconto = false;
        }

    }

    private void mostrarNota(Nota nota) throws IOException {
        VBox layoutNota = new VBox(10);
        layoutNota.setPadding(new Insets(20));
        layoutNota.setPrefWidth(560);
        layoutNota.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: white;");


        Label nomeLoja = new Label("MINHA LOJA S.A.");
        nomeLoja.setStyle("-fx-font-size: 18pt; -fx-font-weight: bold; -fx-alignment: center;");

        Label dataNota = new Label("DATA: " + nota.getDataNota());
        dataNota.setStyle("-fx-font-style: italic;");

        String valorNumeroNota = nota.getNumeroNota() == null || nota.getNumeroNota().trim().isEmpty() ? "" : nota.getNumeroNota();
        String cliente = nota.getCliente() == null || nota.getCliente().trim().isEmpty() ? "" : nota.getCliente();
        String solicitante = nota.getSolicitante() == null || nota.getSolicitante().trim().isEmpty() ? "" : nota.getSolicitante();

        Label numeroNotaLabel = new Label("NOTA Nº: " + valorNumeroNota);
        Label clienteLabel = new Label("CLIENTE: " + cliente);
        Label solicitanteLabel = new Label("SOLICITANTE: " + solicitante);

        VBox cabecalho = new VBox(5, nomeLoja, dataNota, new Separator(), numeroNotaLabel, clienteLabel, solicitanteLabel, new Separator());


        HBox cabecalhoItens = new HBox(25);
        Label colCodigoH = new Label("CÓDIGO");
        Label colDescricaoH = new Label("DESCRIÇÃO");
        Label colQuantidadeH = new Label("QTD");
        Label colPrecoUnitarioH = new Label("PREÇO UNIT.");
        Label colSubtotalH = new Label("SUBTOTAL");


        colCodigoH.setPrefWidth(80);
        HBox.setHgrow(colDescricaoH, Priority.ALWAYS);
        colQuantidadeH.setPrefWidth(60);
        colPrecoUnitarioH.setPrefWidth(120);
        colSubtotalH.setPrefWidth(120);


        colSubtotalH.setAlignment(Pos.CENTER_RIGHT);

        cabecalhoItens.getChildren().addAll(colCodigoH, colDescricaoH, colQuantidadeH, colPrecoUnitarioH, colSubtotalH);
        cabecalhoItens.setStyle("-fx-font-weight: bold;");


        VBox corpoItens = new VBox(5);
        for (Item item : nota.getItens()) {
            HBox linhaItem = new HBox(25);
            Label colCodigo = new Label(item.getCodigo());
            Label colDescricao = new Label(item.getDescricao());
            Label colQuantidade = new Label(String.valueOf(item.getQuantidadeVenda()));
            Label colPrecoUnitario = new Label(String.format("R$ %.2f", item.getPreco()));
            Label colSubtotal = new Label(String.format("R$ %.2f", item.getTotal()));


            colCodigo.setPrefWidth(80);
            HBox.setHgrow(colDescricao, Priority.ALWAYS);
            colQuantidade.setPrefWidth(60);
            colPrecoUnitario.setPrefWidth(110);
            colSubtotal.setPrefWidth(110);
            colSubtotal.setAlignment(Pos.CENTER_RIGHT);

            linhaItem.getChildren().addAll(colCodigo, colDescricao, colQuantidade, colPrecoUnitario, colSubtotal);
            corpoItens.getChildren().add(linhaItem);
        }


        VBox rodape = new VBox(5);
        rodape.setAlignment(Pos.CENTER_RIGHT);
        rodape.setPadding(new Insets(10, 0, 0, 0));
        rodape.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #ccc;");

        Label totalBruto = new Label("Total Bruto: R$ " + nota.getPrecoBruto());
        Label descontoL = new Label("Desconto (%): " + nota.getDesconto() + "%");
        Label totalFinal = new Label("TOTAL FINAL: R$ " + nota.getPrecoFinal());
        totalFinal.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

        rodape.getChildren().addAll(totalBruto, descontoL, totalFinal);

        layoutNota.getChildren().addAll(cabecalho, cabecalhoItens, corpoItens, new Separator(), rodape);

        Button btnAcao = new Button("Confirmar Impressão / Gerar PDF");
        btnAcao.setStyle("-fx-font-size: 12pt; -fx-padding: 8 15;");


        btnAcao.setOnAction(event -> {
            System.out.println("Ação de impressão para a Nota Nº " + nota.getNumeroNota() + " solicitada.");

        });


        HBox containerBotao = new HBox(btnAcao);
        containerBotao.setAlignment(Pos.CENTER);
        containerBotao.setPadding(new Insets(10, 0, 0, 0));


        VBox telaCompleta = new VBox(10, layoutNota, containerBotao);
        telaCompleta.setPadding(new Insets(10));


        Stage palcoPopup = new Stage();
        palcoPopup.initModality(Modality.APPLICATION_MODAL);
        palcoPopup.setTitle("Pré-visualização da Nota de Venda - Nº " + nota.getNumeroNota());


        Scene cena = new Scene(telaCompleta, 800, 800);
        palcoPopup.setScene(cena);
        palcoPopup.show();
    }

}