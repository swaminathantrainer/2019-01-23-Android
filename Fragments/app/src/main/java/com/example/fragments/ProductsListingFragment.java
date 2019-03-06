package com.example.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class ProductsListingFragment extends Fragment implements ProductListAdapter.ProductListAdapterInterface {
    private static final String TAG = "ProductsListingFragment";

    private DatabaseReference mDatabase;
    private DatabaseReference productsRef;

    private ArrayList<Product> productArrayList;
    private ProductListAdapter productListadapter;

    private HomeActivity homeActivityInstance;


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
    public void onAttach(Context context) {
        super.onAttach(context);

        homeActivityInstance = (HomeActivity) context;
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

        productListadapter = new ProductListAdapter(productArrayList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productListadapter);

        Button button = view.findViewById(R.id.createProductButton);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_productsListingFragment_to_createProductFragment));

        EditText productSearchEdt = view.findViewById(R.id.productSearchEdt);
        Button searchBtn = view.findViewById(R.id.searchProductButton);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        productSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, s.toString());
                Log.i(TAG, "Count: " + count);

                String searchString = s.toString();
                productArrayList.clear();

                if (count >= 3) {
                    searchProduct(searchString);
                } else if (count <= 0) {
                    getProducts();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void searchProduct(String searchString) {
        productsRef.orderByChild("name")
                .startAt(searchString)
                .endAt(searchString + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Product product = d.getValue(Product.class);
                            product.setId(dataSnapshot.getKey());

                            productArrayList.add(product);
                            productListadapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getDetails());
                    }
                });
    }

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

    @Override
    public void addProduct(Product product) {
        // adding to the cart

        homeActivityInstance.addItemToCart(product);
    }

    @Override
    public void removeProduct(Product product) {
        // removing from the cart
        homeActivityInstance.removeItemFromCart(product);
    }
}
