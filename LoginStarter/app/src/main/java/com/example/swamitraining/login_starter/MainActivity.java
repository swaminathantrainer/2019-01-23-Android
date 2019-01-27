package com.example.swamitraining.login_starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText emailEdt, passwordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: initialize the View variables
    }

    private boolean validateEmail() {
        String emailRegex = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        String email = emailEdt.getText().toString();
        return checkPattern(emailRegex, email);
    }

    private boolean validatePassword() {
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,15}$";
        String password = passwordEdt.getText().toString();
        return checkPattern(passwordRegex, password);
    }

    // https://developer.android.com/reference/java/util/regex/Pattern
    private static boolean checkPattern(String pattern, String input) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
