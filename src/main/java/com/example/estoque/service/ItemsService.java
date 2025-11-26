package com.example.estoque.service;

import com.example.estoque.model.Item;
import com.example.estoque.repository.ItemDatabaseLoader;

import java.util.ArrayList;
import java.util.List;

public class ItemsService {
    private static final ItemDatabaseLoader ITEM_DATABASE_LOADER = new ItemDatabaseLoader();

    private static ItemsService itemsServiceInstance = null;

    private List<Item> items = new ArrayList<>();

    public void fillItemsFromDb(){
        items = ITEM_DATABASE_LOADER.fillItemsList();
    }

    public static synchronized ItemsService getInstance(){
        if(itemsServiceInstance == null)
            itemsServiceInstance = new ItemsService();

        return itemsServiceInstance;
    }

    public List<Item> getItems() {
        return items;
    }
}
