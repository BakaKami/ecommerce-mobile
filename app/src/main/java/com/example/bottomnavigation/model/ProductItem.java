package com.example.bottomnavigation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductItem implements Parcelable {

    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private double price;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    public ProductItem(Parcel in) {
        image = in.readString();
        price = in.readDouble();
        description = in.readString();
        id = in.readInt();
        title = in.readString();
        category = in.readString();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public ProductItem() {

    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }

    public Rating getRating(){
        return rating;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(category);
    }
}
