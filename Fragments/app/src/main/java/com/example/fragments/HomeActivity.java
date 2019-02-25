package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

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
    NavController navController;

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
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
