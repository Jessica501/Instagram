package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instagram.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = String.valueOf(binding.etUsername.getText());
                String password = String.valueOf(binding.etPassword.getText());
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        // TODO: navigate to main activity if user has signed in properly
    }
}