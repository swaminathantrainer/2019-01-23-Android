package com.example.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragments.pojo.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProductFragment extends Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference productsRef;
    private DatabaseReference productReference;

    private EditText nameEdt, descEdt, stockCountEdt;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        productsRef = mDatabase.child(Constants.PRODUCTS_TABLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.create_product_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEdt = view.findViewById(R.id.editText_name);
        descEdt = view.findViewById(R.id.editText_desc);
        stockCountEdt = view.findViewById(R.id.editText_stock_count);

        Button createProductBtn = view.findViewById(R.id.create_product_btn);
        createProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productReference = productsRef.push();
                String productKey = productReference.getKey();

                String name = nameEdt.getText().toString();
                String desc = descEdt.getText().toString();
                int stock = Integer.parseInt(stockCountEdt.getText().toString());

                insertProduct(productKey, name, desc, stock);
            }
        });
    }

    private void insertProduct(String id, String name, String desc, int stock) {
        Product product = new Product(id, name, desc, stock);
        productReference.setValue(product);
    }
}
