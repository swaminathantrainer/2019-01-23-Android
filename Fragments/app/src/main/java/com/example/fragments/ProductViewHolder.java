package com.example.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTxt, stockCountTxt;
    private Button addProductBtn, removeProductBtn;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTxt = itemView.findViewById(R.id.productTitleTxt);
        stockCountTxt = itemView.findViewById(R.id.stockCountTxt);
        addProductBtn = itemView.findViewById(R.id.addProductBtn);
        removeProductBtn = itemView.findViewById(R.id.removeProductBtn);
    }

    public void setData(String title, int stockCount) {
        titleTxt.setText(title);
        stockCountTxt.setText("" + stockCount);
    }
}
