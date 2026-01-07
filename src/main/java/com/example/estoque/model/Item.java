package com.example.estoque.model;

public class Item {
    private String codigo;
    private String descricao;
    private double preco;
    private int quantidade;
    private String marca;
    private int estante;
    private String prateleira;
    private int quantidadeVenda;

    public Item(String codigo, String descricao, double preco, int quantidade, String marca, int estante, String prateleira) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.marca = marca;
        this.estante = estante;
        this.prateleira = prateleira;
        this.quantidadeVenda = 0;
    }

    public Item(){}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEstante() {
        return estante;
    }

    public void setEstante(int estante) {
        this.estante = estante;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public int getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(int quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }

    public double getTotal(){
        return getPreco() * getQuantidadeVenda();
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + codigo + '\'' +
                ", description='" + descricao + '\'' +
                ", price=" + preco +
                ", amount=" + quantidade +
                ", brand='" + marca + '\'' +
                ", shelf=" + estante +
                ", shelfLevel='" + prateleira + '\'' +
                '}';
    }
}
