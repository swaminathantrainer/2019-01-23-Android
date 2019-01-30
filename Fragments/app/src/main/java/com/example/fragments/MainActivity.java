package com.example.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // 1. get activity's fragment manager
        // 2. start transaction
        // 3. do the action
        // 4. commit

        LoginFragment loginFragment = new LoginFragment();

        // add login page
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.auth_host_container, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
