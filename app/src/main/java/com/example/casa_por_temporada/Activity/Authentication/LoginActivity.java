package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.casa_por_temporada.R;

public class LoginActivity extends AppCompatActivity {

    private TextView textCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        referComponents();
        configClicks();
    }

    private void configClicks(){

        textCreateAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });

    }
    private void referComponents(){

        textCreateAccount = findViewById(R.id.text_createAccount);

    }
}