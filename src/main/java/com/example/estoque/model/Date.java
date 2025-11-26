package com.example.estoque.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    private LocalDate today = LocalDate.now();
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String formattedDate = today.format(FORMATTER);

    public String getFormattedDate() {
        return formattedDate;
    }
}
