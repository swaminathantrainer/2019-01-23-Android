package com.example.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRowViewHolder> {
    private String[] cityNames;

    public CityRecyclerAdapter(String[] c) {
        cityNames = c;
    }

    @NonNull
    @Override
    public CityRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        CityRowViewHolder cityRowViewHolder = new CityRowViewHolder(row);
        return cityRowViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityRowViewHolder cityRowViewHolder, int position) {
        String city = cityNames[position];
        cityRowViewHolder.setCity(city);
    }

    @Override
    public int getItemCount() {
        return cityNames.length;
    }
}
