package com.example.bottomnavigation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.adapter.WishlistAdapter;
import com.example.bottomnavigation.fragment.WishlistFragment;
import com.example.bottomnavigation.model.ProductItem;
import com.example.bottomnavigation.model.Rating;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.ProductData;
import com.example.bottomnavigation.room.model.WishlistData;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String CURRENT_ID = "current-id";

    String title, ratingRate, ratingCount, price, description, image;
    ImageView imgDetail;
    TextView tvTitle, tvRating, tvRatingCount, tvPrice, tvDescription;
    Button btnBuy, btnAddWishlist;

    ProductData productData;
    RoomDB database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        database = RoomDB.getInstance(ProductDetailActivity.this);

        tvTitle = findViewById(R.id.tv_product_detail_title);
        tvRating = findViewById(R.id.tv_product_detail_rating);
        tvRatingCount = findViewById(R.id.tv_product_detail_rating_count);
        tvPrice = findViewById(R.id.tv_product_detail_price);
        tvDescription = findViewById(R.id.tv_product_detail_description);
        imgDetail = findViewById(R.id.img_product_detail);
        btnAddWishlist = findViewById(R.id.btn_add_wishlist);
        btnBuy = findViewById(R.id.btn_buy);


        int currentId = getIntent().getExtras().getInt(CURRENT_ID);

        productData = database.productDao().getDataById(currentId);
        title = productData.getTitle();
        ratingRate = Double.toString(productData.getRatingRate());
        ratingCount = Integer.toString(productData.getRatingCount());
        price = Double.toString(productData.getPrice());
        description = productData.getDescription();
        image = productData.getImage();

        tvTitle.setText(title);
        tvRating.setText(ratingRate);
        tvRatingCount.setText(String.format("(%s)", ratingCount));
        tvPrice.setText("$ " + price);
        tvDescription.setText(description);
        Glide.with(getApplicationContext())
                .load(image)
                .into(imgDetail);

        btnAddWishlist.setOnClickListener(v -> {

            if (!isItemExist(productData.getId())) {
                WishlistData newWishlist = new WishlistData();
                newWishlist.setTitle(title);
                newWishlist.setCategory(productData.getCategory());
                newWishlist.setDescription(description);
                newWishlist.setProductId(productData.getId());
                newWishlist.setImage(image);
                newWishlist.setPrice(Double.parseDouble(price));
                newWishlist.setRatingRate(Double.parseDouble(ratingRate));
                newWishlist.setRatingCount(Integer.parseInt(ratingCount));

                database.wishlistDao().insert(newWishlist);

                btnAddWishlist.setText("REMOVE FROM WISHLIST");

                // Snackbar here
                Snackbar.make(v, "Wishlist added", Snackbar.LENGTH_INDEFINITE).setAction("OK", vi -> {
                    // dismiss
                }).show();

            } else {
                database.wishlistDao().deleteById(productData.getId());

                btnAddWishlist.setText("ADD TO WISHLIST");

                // Snackbar here
                Snackbar.make(v, "Item has removed from wishlist", Snackbar.LENGTH_INDEFINITE).setAction("OK", vi -> {
                    // dismiss
                }).show();
            }
        });


        btnBuy.setOnClickListener(v -> {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT)
                    .show();
        });

        if(isItemExist(productData.getId()))
            btnAddWishlist.setText("REMOVE FROM WISHLIST");
        else
            btnAddWishlist.setText("ADD TO WISHLIST");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private boolean isItemExist(int id) {
        List<WishlistData> wishlist = database.wishlistDao().getAllData();

        for (WishlistData item : wishlist) {
            // check if item already in wishlist
            if (item.getProductId() == id){
                return true;
            }
        }

        return false;
    }

}
