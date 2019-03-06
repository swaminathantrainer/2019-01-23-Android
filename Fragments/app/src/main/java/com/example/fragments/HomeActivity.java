package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fragments.pojo.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

/*
JSON Object
s = {
    name: "Swami",
    age: "10",
    rollNumber: "12345",
    address: {
        city: "Chennai",
        houseNo: "1"
    }
}

s.address.city;
// Chennai;

s.age
// 10;

JSON Array
arr = [
    {
        name: "Swami",
        age: "10",
        rollNumber: "12345",
        address: {
            city: "Chennai",
            houseNo: "1"
        }
    },
    {
        name: "Rahul",
        age: "11",
        rollNumber: "112345",
        address: null
    }
]

arr[0].name;
// Swami;

 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    NavController navController;
    RelativeLayout cartContainer;
    TextView cartCountTxt;
    HashMap<String, ArrayList<Product>> cartItems = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

        Button logout = findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

                Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .build();
        setupActionBarWithNavController(this, navController);

        cartContainer = findViewById(R.id.cartContainer);
        cartCountTxt = findViewById(R.id.cartCount);

        Button cartProceedBtn = findViewById(R.id.cartProceedBtn);
        cartProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        refreshCartUI();
    }

    public void addItemToCart(Product product) {
        Log.i(TAG, "ADDING: " + product.getId());

        // Check if the particular product exists
        // if exists -> append to the list
        // else -> create a new list

        if (cartItems.containsKey(product.getId())) {
            ArrayList<Product> givenProductsInCart = cartItems.get(product.getId());
            givenProductsInCart.add(product);
        } else {
            ArrayList<Product> givenProductsInCart = new ArrayList<>();
            givenProductsInCart.add(product);
            cartItems.put(product.getId(), givenProductsInCart);
        }

        refreshCartUI();
    }

    public void removeItemFromCart(Product product) {
        Log.i(TAG, "REMOVING: " + product.getId());

        // Check if the particular product exists
        // if exists -> remove from the list
        // else -> do nothing

        if (cartItems.containsKey(product.getId())) {
            ArrayList<Product> givenProductsInCart = cartItems.get(product.getId());
            if (givenProductsInCart.size() > 0)
                givenProductsInCart.remove(givenProductsInCart.size() - 1);
        }

        refreshCartUI();
    }

    public void clearCart() {
        refreshCartUI();
    }

    private void refreshCartUI() {
        int cartCount = getCartCount();
        if (cartCount > 0) {
            showCartUI();
            updateCartCount(cartCount);
        } else {
            hideCartUI();
        }
    }

    private void updateCartCount(int count) {
        cartCountTxt.setText("" + count);
    }

    private void showCartUI() {
        cartContainer.setVisibility(View.VISIBLE);
    }

    private void hideCartUI() {
        cartContainer.setVisibility(View.INVISIBLE);
    }

    private int getCartCount() {
        int count = 0;
        for (Map.Entry<String, ArrayList<Product>> cartItem : cartItems.entrySet()) {
            for (Product p : cartItem.getValue()) {
                count++;
            }
        }

        return count;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
