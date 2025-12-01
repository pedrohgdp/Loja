package com.example.estoque.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
    private LocalDate hoje = LocalDate.now();
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String dataFormatada = hoje.format(FORMATTER);

    public String getDataFormatada() {
        return dataFormatada;
    }
}
