package com.example.bottomnavigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.activity.ProductDetailActivity;
import com.example.bottomnavigation.model.ProductItem;
import com.example.bottomnavigation.model.Rating;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.ProductData;

import java.util.List;
import java.util.Optional;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductItem> resultList;
    private RoomDB database;

    public ProductAdapter(Context context, List<ProductItem> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_list, parent, false);

        ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(view);
        viewHolder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), ProductDetailActivity.class);
//            ProductItem item = new ProductItem();
//            item.setId(resultList.get(viewHolder.getAdapterPosition()).getId());
            int currentId = resultList.get(viewHolder.getAdapterPosition()).getId();

            intent.putExtra(ProductDetailActivity.CURRENT_ID, currentId);

            parent.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductItem productItem = resultList.get(position);

        holder.tvTitle.setText(productItem.getTitle());
        holder.tvCategory.setText(productItem.getCategory());
        holder.tvRating.setText(Double.toString(productItem.getRating().getRate()));
        holder.tvPrice.setText("$ " + productItem.getPrice());

        // load picture
        Glide.with(context)
                .load(productItem.getImage())
                .into(holder.imgPoster);

        // initialize database
        database = RoomDB.getInstance(context);
        // initialize new db object
        ProductData productData = new ProductData();

        productData.setRatingCount(productItem.getRating().getCount());
        productData.setRatingRate(productItem.getRating().getRate());
        productData.setPrice(productItem.getPrice());
        productData.setDescription(productItem.getDescription());
        productData.setId(productItem.getId());
        productData.setCategory(productItem.getCategory());
        productData.setTitle(productItem.getTitle());
        productData.setImage(productItem.getImage());

        // insert into db
        database.productDao().insert(productData);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView tvTitle, tvCategory, tvRating, tvPrice;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_product);
            tvTitle = itemView.findViewById(R.id.tv_product_list_title);
            tvCategory = itemView.findViewById(R.id.tv_product_list_category);
            tvRating = itemView.findViewById(R.id.tv_product_list_rating);
            tvPrice = itemView.findViewById(R.id.tv_product_list_price);
            relativeLayout = itemView.findViewById(R.id.layout_product);
        }
    }
}
