package com.example.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragments.pojo.Product;

import java.util.ArrayList;

public class ProductListadapter extends RecyclerView.Adapter<ProductViewHolder> {
    private ArrayList<Product> productArrayList;

    public ProductListadapter(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater
                .from(viewGroup.getContext()).inflate(R.layout.product_row, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(row);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Product product = productArrayList.get(i);
        productViewHolder.setData(product.getName(), product.getStockCount());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public int getPositionOfItem(String productId) {
        for (int i = 0; i < productArrayList.size(); i++) {
            Product product = productArrayList.get(i);
            if (product.getId().equals(productId)) {
                return i;
            }
        }

        return -1;
    }
}
