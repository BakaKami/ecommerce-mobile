package com.example.bottomnavigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.activity.ProductDetailActivity;
import com.example.bottomnavigation.model.ProductItem;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.WishlistData;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistData> dataList;
    private Context context;
    private RoomDB database;

    public WishlistAdapter(List<WishlistData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist, parent, false);

        WishlistAdapter.ViewHolder viewHolder = new WishlistAdapter.ViewHolder(view);

        viewHolder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), ProductDetailActivity.class);
//            ProductItem item = new ProductItem();
//            item.setId(dataList.get(viewHolder.getAdapterPosition()).getProductId());
            int currentId = dataList.get(viewHolder.getAdapterPosition()).getProductId();

            intent.putExtra(ProductDetailActivity.CURRENT_ID, currentId);

            parent.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // initialize product data
        WishlistData productData = dataList.get(position);
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
            WishlistData item = dataList.get(i);
            // delete from database
            database.wishlistDao().delete(item);
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
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnAddWishlist = itemView.findViewById(R.id.btn_add_wishlist);
            btnRemoveWishlist = itemView.findViewById(R.id.btn_remove_wishlist);
            imgPoster = itemView.findViewById(R.id.wishlist_img_product);
            tvTitle = itemView.findViewById(R.id.tv_wishlist_product_list_title);
            tvCategory = itemView.findViewById(R.id.tv_wishlist_product_list_category);
            tvRating = itemView.findViewById(R.id.tv_wishlist_product_list_rating);
            tvPrice = itemView.findViewById(R.id.tv_wishlist_product_list_price);
            relativeLayout = itemView.findViewById(R.id.layout_wishlist_product);
        }
    }
}
