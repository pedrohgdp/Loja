package com.example.estoque.model;

public class Cliente {
    private Integer codigo;
    private String nome;
    private Double limiteUsado;
    private Double limiteTotal;
    private String cpfCnpj;

    public Cliente(Integer codigo, String nome, double limiteTotal, double limiteUsado, String cpfCnpj) {
        this.codigo = codigo;
        this.nome = nome;
        this.limiteUsado = limiteUsado;
        this.limiteTotal = limiteTotal;
        this.cpfCnpj = cpfCnpj;
    }

    public Cliente() {

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLimiteUsado() {
        return limiteUsado;
    }

    public void setLimiteUsado(Double limiteUsado) {
        this.limiteUsado = limiteUsado;
    }

    public Double getLimiteTotal() {
        return limiteTotal;
    }

    public void setLimiteTotal(Double limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
}
