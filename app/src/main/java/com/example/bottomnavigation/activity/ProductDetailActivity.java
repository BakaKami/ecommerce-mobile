package com.example.bottomnavigation.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.model.ProductItem;
import com.example.bottomnavigation.model.Rating;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.ProductData;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "extra_product";
    public static final String EXTRA_RATING = "extra_rating";

    String title, ratingRate, ratingCount, price, description, image;
    ImageView imgDetail;
    TextView tvTitle, tvRating, tvRatingCount, tvPrice, tvDescription;
    ProductItem item;
    Rating rating;
    Button btnBuy, btnAddWishlist;

    RoomDB database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        tvTitle = findViewById(R.id.tv_product_detail_title);
        tvRating = findViewById(R.id.tv_product_detail_rating);
        tvRatingCount = findViewById(R.id.tv_product_detail_rating_count);
        tvPrice = findViewById(R.id.tv_product_detail_price);
        tvDescription = findViewById(R.id.tv_product_detail_description);
        imgDetail = findViewById(R.id.img_product_detail);

        btnAddWishlist = findViewById(R.id.btn_add_wishlist);
        btnBuy = findViewById(R.id.btn_buy);

        item = getIntent().getParcelableExtra(EXTRA_PRODUCT);
        rating = getIntent().getParcelableExtra(EXTRA_RATING);

        title = item.getTitle();

        if(rating != null){
            ratingRate = Double.toString(rating.getRate());
            ratingCount = Integer.toString(rating.getCount());
        }

        price = Double.toString(item.getPrice());
        description = item.getDescription();
        image = item.getImage();

        tvTitle.setText(title);
        tvRating.setText(ratingRate);
        tvRatingCount.setText(String.format("(%s)", ratingCount));
        tvPrice.setText("$ " + price);
        tvDescription.setText(description);
        Glide.with(getApplicationContext())
                .load(image)
                .into(imgDetail);

        btnAddWishlist.setOnClickListener(v -> {
            ProductData newWishlist = new ProductData();
            newWishlist.setTitle(title);
            newWishlist.setCategory(item.getCategory()); // need to be filled
            newWishlist.setDescription(description);
            newWishlist.setId(item.getId()); // need to be filled
            newWishlist.setImage(image);
            newWishlist.setPrice(Double.parseDouble(price));
            newWishlist.setRatingRate(Double.parseDouble(ratingRate));
            newWishlist.setRatingCount(Integer.parseInt(ratingCount));

            database = RoomDB.getInstance(ProductDetailActivity.this);
            database.productDao().insert(newWishlist);

            // TOAST here
            Toast.makeText(this, "Wishlist added", Toast.LENGTH_SHORT);
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
