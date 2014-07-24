package com.tw.kampala.androidbootcamp.service.api;

import com.tw.kampala.androidbootcamp.models.Item;
import retrofit.http.GET;

import java.util.List;

public interface ItemAPI {

    @GET("/items")
    public List<Item> getItems();

}
