package com.example.swamitraining.uibasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText emailEdt, passwordEdt;
    Button loginButton;
    TextView emailErrorTextView, passwordErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEdt = findViewById(R.id.emailInput);
        passwordEdt = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        emailErrorTextView = findViewById(R.id.emailErrorTextView);
        emailErrorTextView.setText("The email is not valid");
        emailErrorTextView.setVisibility(View.INVISIBLE);

        // Revealing Constructor
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = validateEmail() && validatePassword();
                if (result) {
                    // success
                    Log.i("Login", "Validation Success!");
                } else {
                    // error
                    Log.i("Login", "Error during validation");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // swaminathantrainer@gmail.com -> true
    // swaminathantrainergmail.com -> false
    private boolean validateEmail() {
        String email = emailEdt.getText().toString();
        String regex = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        Boolean result = matcher(regex, email);

        if (result) {
            emailErrorTextView.setVisibility(View.INVISIBLE);
        } else {
            emailErrorTextView.setVisibility(View.VISIBLE);
        }

        return result;
    }

    private boolean validatePassword() {
        return true;
    }

    private boolean matcher(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
