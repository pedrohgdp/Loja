package com.example.estoque.model;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Note {
    private final String noteNumber;
    private final String client;
    private final String requestingParty;
    private final ObservableList<Item> items;
    private final String grossPrice;
    private final String discount;
    private final String finalPrice;
    private String noteDate;

    private LocalDate actualDate = LocalDate.now();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String formatedDate = actualDate.format(formatter);

    public Note(String noteNumber, String client, String requestingParty, ObservableList<Item> items, String grossPrice, String discount, String finalPrice) {
        this.noteNumber = noteNumber;
        this.client = client;
        this.requestingParty = requestingParty;
        this.items = items;
        this.grossPrice = grossPrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.noteDate = formatedDate;
    }

    public String getNoteNumber() { return noteNumber; }
    public String getClient() { return client; }
    public String getRequestingParty() { return requestingParty; }
    public ObservableList<Item> getItems() { return items; }
    public String getGrossPrice() { return grossPrice; }
    public String getDiscount() { return discount; }
    public String getFinalPrice() { return finalPrice; }
    public String getNoteDate() { return noteDate; }
}
