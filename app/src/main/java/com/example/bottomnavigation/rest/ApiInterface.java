package com.example.bottomnavigation.rest;

import com.example.bottomnavigation.model.ProductItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/products")
    Call<List<ProductItem>> getProducts();
}
