package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.casa_por_temporada.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView textOnToolBar;
    private Button btnResetPassword;
    private ImageButton btnGetBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        referComponents();
        setTextOnToolBar();
        configClicks();

    }

    private void setTextOnToolBar(){

        textOnToolBar.setText("Recuperar senha");

    }
    private void referComponents(){

        btnResetPassword = findViewById(R.id.btn_reset_password);
        textOnToolBar = findViewById(R.id.text_main_toolbar);
        btnGetBack = findViewById(R.id.btn_getback);
    }
    private void configClicks(){

        btnGetBack.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

    }

}