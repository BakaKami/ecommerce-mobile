package com.example.bottomnavigation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    WishlistAdapter productDataAdapter;

    List<WishlistData> dataList = new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        btnRemove = view.findViewById(R.id.btn_remove_wishlist);
        recyclerView = view.findViewById(R.id.rv_wishlist);

        // initialize database
        database = RoomDB.getInstance(getActivity());
        // store database value in list
        dataList = database.wishlistDao().getAllData();
        // initialize adapter
        productDataAdapter = new WishlistAdapter(dataList, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(productDataAdapter);

        // notify when data is inserted
        dataList.clear();
        dataList.addAll(database.wishlistDao().getAllData());
        productDataAdapter.notifyDataSetChanged();

        return view;
    }
}