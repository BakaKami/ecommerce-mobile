package com.example.bottomnavigation.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bottomnavigation.room.model.WishlistData;

import java.util.List;

@Dao
public interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WishlistData wishlistData);

    @Delete
    void delete(WishlistData wishlistData);

    @Query("SELECT * from wishlist")
    List<WishlistData> getAllData();
}
