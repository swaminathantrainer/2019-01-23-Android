package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextView errorTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorTxt = view.findViewById(R.id.emailErrorTextView);

        final EditText editText = view.findViewById(R.id.emailInput);
        final EditText passwordEdt = view.findViewById(R.id.passwordInput);

        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signupFragment));

        Button onboardingButton = view.findViewById(R.id.onboarding_button);

        onboardingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();

                final Bundle bundle = new Bundle();
                bundle.putString("email", email);

                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_onboardingFragment, bundle);
            }
        });

        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                String password = passwordEdt.getText().toString();

                signInUser(email, password);
            }
        });

//        OnboardingFragment onboardingFragment = OnboardingFragment.getObject("swaaminathanm@gmail.com");

//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SignupFragment signupFragment = new SignupFragment();
//
////                 add login page
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.auth_host_container, signupFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
    }

    private void openHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(intent);
    }

    private void showError(String error) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(error);
    }

    private void hideError() {
        errorTxt.setVisibility(View.INVISIBLE);
    }

    private void signInUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    hideError();

                    FirebaseUser user = mAuth.getCurrentUser();

                    openHomeActivity();
                } else {
                    showError("Error while signup");
                }
            }
        });
    }
}
