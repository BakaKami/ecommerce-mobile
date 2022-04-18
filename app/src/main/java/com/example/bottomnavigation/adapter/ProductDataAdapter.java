package com.example.bottomnavigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.ProductData;

import java.util.List;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> {

    private List<ProductData> dataList;
    private Context context;
    private RoomDB database;

    public ProductDataAdapter(List<ProductData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist, parent, false);

        return new ProductDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // initialize product data
        ProductData productData = dataList.get(position);
        // initialize database
        database = RoomDB.getInstance(context);

        holder.tvTitle.setText(productData.getTitle());
        holder.tvCategory.setText(productData.getCategory());
        holder.tvRating.setText(Double.toString(productData.getRatingRate()));
        holder.tvPrice.setText("$ " + productData.getPrice());

        // load picture
        Glide.with(context)
                .load(productData.getImage())
                .into(holder.imgPoster);

        holder.btnRemoveWishlist.setOnClickListener(v -> {
            int i = holder.getAdapterPosition();

            // initialize product data
            ProductData item = dataList.get(i);
            // delete from database
            database.productDao().delete(item);
            // notify changes
            dataList.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, dataList.size());
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnAddWishlist;
        ImageView btnRemoveWishlist, imgPoster;
        TextView tvTitle, tvCategory, tvRating, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnAddWishlist = itemView.findViewById(R.id.btn_add_wishlist);
            btnRemoveWishlist = itemView.findViewById(R.id.btn_remove_wishlist);
            imgPoster = itemView.findViewById(R.id.wishlist_img_product);
            tvTitle = itemView.findViewById(R.id.tv_wishlist_product_list_title);
            tvCategory = itemView.findViewById(R.id.tv_wishlist_product_list_category);
            tvRating = itemView.findViewById(R.id.tv_wishlist_product_list_rating);
            tvPrice = itemView.findViewById(R.id.tv_wishlist_product_list_price);
        }
    }
}
