package com.example.estoque.model;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota {
    private final String numeroNota;
    private final String cliente;
    private final String solicitante;
    private final ObservableList<Item> itens;
    private final String precoBruto;
    private final String desconto;
    private final String precoFinal;
    private String dataNota;

    private LocalDate actualDate = LocalDate.now();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String formatedDate = actualDate.format(formatter);

    public Nota(String numeroNota, String cliente, String solicitante, ObservableList<Item> itens, String precoBruto, String desconto, String precoFinal) {
        this.numeroNota = numeroNota;
        this.cliente = cliente;
        this.solicitante = solicitante;
        this.itens = itens;
        this.precoBruto = precoBruto;
        this.desconto = desconto;
        this.precoFinal = precoFinal;
        this.dataNota = formatedDate;
    }

    public String getNumeroNota() { return numeroNota; }
    public String getCliente() { return cliente; }
    public String getSolicitante() { return solicitante; }
    public ObservableList<Item> getItens() { return itens; }
    public String getPrecoBruto() { return precoBruto; }
    public String getDesconto() { return desconto; }
    public String getPrecoFinal() { return precoFinal; }
    public String getDataNota() { return dataNota; }
}
