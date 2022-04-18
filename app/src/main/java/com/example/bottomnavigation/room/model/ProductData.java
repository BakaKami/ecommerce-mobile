package com.example.bottomnavigation.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product")
public class ProductData implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;

    private String image;
    private double price;
    @ColumnInfo(name = "rating_rate")
    private double ratingRate;
    @ColumnInfo(name = "rating_count")
    private int ratingCount;
    private String description;
    private String title;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(double ratingRate) {
        this.ratingRate = ratingRate;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
