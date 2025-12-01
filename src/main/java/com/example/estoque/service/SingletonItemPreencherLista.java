package com.example.estoque.service;

import com.example.estoque.model.Item;
import com.example.estoque.repository.CarregarItemDB;

import java.util.ArrayList;
import java.util.List;

public class SingletonItemPreencherLista {
    private static final CarregarItemDB CARREGAR_ITEM_DB = new CarregarItemDB();

    private static SingletonItemPreencherLista singletonItemPreencherListaInstance = null;

    private List<Item> itens = new ArrayList<>();

    public void preencherItensViaDB(){
        itens = CARREGAR_ITEM_DB.preencherListaItens();
    }

    public static synchronized SingletonItemPreencherLista getInstance(){
        if(singletonItemPreencherListaInstance == null)
            singletonItemPreencherListaInstance = new SingletonItemPreencherLista();

        return singletonItemPreencherListaInstance;
    }

    public List<Item> getItens() {
        return itens;
    }
}
