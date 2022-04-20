package com.example.bottomnavigation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.R;
import com.example.bottomnavigation.adapter.WishlistAdapter;
import com.example.bottomnavigation.room.RoomDB;
import com.example.bottomnavigation.room.model.WishlistData;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView btnRemove;
    RoomDB database;
    WishlistAdapter wishlistAdapter;
    TextView tvEmptyWishlist;

    public static List<WishlistData> dataList = new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        tvEmptyWishlist.setVisibility(View.INVISIBLE);

        database = RoomDB.getInstance(getActivity());

        // clear list
        dataList.clear();
        // reassign data
        dataList.addAll(database.wishlistDao().getAllData());
        // notify changes
        wishlistAdapter.notifyDataSetChanged();

        if (dataList.isEmpty() || dataList == null) {
            tvEmptyWishlist.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        btnRemove = view.findViewById(R.id.btn_remove_wishlist);
        recyclerView = view.findViewById(R.id.rv_wishlist);
        tvEmptyWishlist = view.findViewById(R.id.tv_empty_wishlist_fragment);

        tvEmptyWishlist.setVisibility(View.INVISIBLE);

        // initialize database
        database = RoomDB.getInstance(getActivity());
        // store database value in list
        dataList = database.wishlistDao().getAllData();
        // initialize adapter
        wishlistAdapter = new WishlistAdapter(dataList, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wishlistAdapter);

        // notify when data is inserted
        dataList.clear();
        dataList.addAll(database.wishlistDao().getAllData());
        wishlistAdapter.notifyDataSetChanged();

        if (dataList.isEmpty() || dataList == null) {
            tvEmptyWishlist.setVisibility(View.VISIBLE);
        }

        return view;
    }
}