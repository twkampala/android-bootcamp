package com.tw.kampala.androidbootcamp.service.api;

import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedByteArray;

import java.util.List;

public interface ItemAPI {

    @GET("/items/")
    public ItemIds getItems();

    @GET("/items/{id}")
    public Item getItem(@Path("id") String id);

    @GET("/items/{id}/avatar")
    public Response getItemImage(@Path("id") String id);

}
