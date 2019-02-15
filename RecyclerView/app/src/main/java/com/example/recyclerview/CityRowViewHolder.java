package com.example.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CityRowViewHolder extends RecyclerView.ViewHolder {
    private TextView cityTextView;

    public CityRowViewHolder(@NonNull View itemView) {
        super(itemView);

        cityTextView = itemView.findViewById(R.id.cityText);
    }

    public void setCity(String city) {
        cityTextView.setText(city);
    }

}
