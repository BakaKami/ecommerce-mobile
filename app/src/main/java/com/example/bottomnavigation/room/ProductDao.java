package com.example.bottomnavigation.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bottomnavigation.room.model.ProductData;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductData productData);

    @Delete
    void delete(ProductData productData);

    @Query("SELECT * from product")
    List<ProductData> getAllData();
}
