package com.example.estoque.model;

public class Item {
    private String codigo;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private String marca;
    private Integer estante;
    private String prateleira;
    private Integer quantidadeVenda;

    public Item(String codigo, String descricao, double preco, Integer quantidade, String marca, Integer estante, String prateleira) {
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

    public Integer getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(Integer amoutSold) {
        this.quantidadeVenda = amoutSold;
    }

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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getEstante() {
        return estante;
    }

    public void setEstante(Integer estante) {
        this.estante = estante;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getTotal(){
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
