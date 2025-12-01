package com.example.estoque.service;

import com.example.estoque.model.Item;
import com.example.estoque.repository.CarregarItemDB;

import java.util.ArrayList;
import java.util.List;

public class SingletonPreencherLista {
    private static final CarregarItemDB CARREGAR_ITEM_DB = new CarregarItemDB();

    private static SingletonPreencherLista singletonPreencherListaInstance = null;

    private ArrayList<Item> itens = new ArrayList<>();

    public void preencherItensViaDB(){
        itens = CARREGAR_ITEM_DB.preencherListaItens();
    }

    public static synchronized SingletonPreencherLista getInstance(){
        if(singletonPreencherListaInstance == null)
            singletonPreencherListaInstance = new SingletonPreencherLista();

        return singletonPreencherListaInstance;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }
}
