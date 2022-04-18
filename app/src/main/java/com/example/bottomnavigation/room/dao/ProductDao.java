package com.example.bottomnavigation.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bottomnavigation.room.model.ProductData;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductData productData);

    @Query("SELECT * from product")
    List<ProductData> getAllData();

    @Query("SELECT * from product WHERE id = :id")
    ProductData getDataById(int id);
}
