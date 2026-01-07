package com.example.estoque.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

public class Venda {
    private final ObservableList<Item> listaItens = FXCollections.observableArrayList();
    private double descontoPercentual = 0.0;

    // Use primitives for logic; it's faster and cleaner
    public void adicionarOuAtualizarItem(Item item, int quantidade, double preco) {
        if (item == null) return;

        Optional<Item> itemExiste = listaItens.stream()
                .filter(i -> i.getDescricao().equals(item.getDescricao()))
                .findFirst();

        if (itemExiste.isPresent()) {
            Item encontrado = itemExiste.get();
            // No need for Integer.valueOf(), Java does this automatically
            encontrado.setQuantidadeVenda(encontrado.getQuantidadeVenda() + quantidade);
        } else {
            item.setQuantidadeVenda(quantidade);
            item.setPreco(preco);
            listaItens.add(item);
        }
    }

    public void removerItem(Item item, Integer qtdParaRemover) {
        if (item == null) return;

        // If qtdParaRemover is null, we assume "remove all"
        if (qtdParaRemover == null || qtdParaRemover >= item.getQuantidadeVenda()) {
            listaItens.remove(item);
        } else {
            item.setQuantidadeVenda(item.getQuantidadeVenda() - qtdParaRemover);
        }
    }

    public double getPrecoBruto() {
        return listaItens.stream().mapToDouble(Item::getTotal).sum();
    }

    public double getPrecoFinal() {
        return getPrecoBruto() * (1 - (descontoPercentual / 100));
    }

    public double calcularDescontoPorPrecoFinal(double precoFinalInformado) {
        double bruto = getPrecoBruto();
        if (bruto <= 0) return 0.0;
        return (1 - (precoFinalInformado / bruto)) * 100;
    }

    public ObservableList<Item> getListaItens() { return listaItens; }
    public double getDescontoPercentual() { return descontoPercentual; }
    public void setDescontoPercentual(double desconto) { this.descontoPercentual = desconto; }
}