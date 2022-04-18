package com.example.bottomnavigation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomnavigation.R;
import com.example.bottomnavigation.adapter.ProductAdapter;
import com.example.bottomnavigation.model.ProductItem;
import com.example.bottomnavigation.rest.ApiClient;
import com.example.bottomnavigation.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ProductAdapter adapter;
    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        callRetrofit();

        return view;
    }

    private void callRetrofit() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductItem>> itemCall = apiInterface.getProducts();
        itemCall.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                List<ProductItem> itemList = response.body();
                adapter = new ProductAdapter(getActivity(), itemList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {

            }
        });
    }
}