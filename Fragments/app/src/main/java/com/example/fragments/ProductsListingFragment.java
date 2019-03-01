package com.example.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragments.pojo.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListingFragment extends Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference productsRef;

    private ArrayList<Product> productArrayList;
    private ProductListadapter productListadapter;


    public ProductsListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        productsRef = mDatabase.child(Constants.PRODUCTS_TABLE);

        productArrayList = new ArrayList<>();

        getProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.productsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        productListadapter = new ProductListadapter(productArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productListadapter);

        Button button = view.findViewById(R.id.createProductButton);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_productsListingFragment_to_createProductFragment));
    }

//    private void getProducts() {
//        showLoading();
//
//        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
//                    Product product = productSnapshot.getValue(Product.class);
//                    Log.i("SAMPLE_APP", product.getName());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                showError(databaseError.getMessage());
//            }
//        });
//    }

    private void getProducts() {
        productsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product = dataSnapshot.getValue(Product.class);
                product.setId(dataSnapshot.getKey());
                productArrayList.add(product);
                productListadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String productId = dataSnapshot.getKey();
                int pos = productListadapter.getPositionOfItem(productId);

                Product product = dataSnapshot.getValue(Product.class);
                product.setId(dataSnapshot.getKey());

                productArrayList.set(pos, product);

                productListadapter.notifyItemChanged(pos);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showError(databaseError.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
    }
}
