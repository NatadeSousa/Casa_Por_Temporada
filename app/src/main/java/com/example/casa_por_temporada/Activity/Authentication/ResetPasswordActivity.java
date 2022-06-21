package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView textOnToolBar;
    private Button btnResetPassword;
    private ImageButton btnGetBack;
    private EditText editEmail;
    private ProgressBar progressBarResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        referComponents();
        setTextOnToolBar();
        configClicks();

    }


    private void validateUserData(){

        String email = editEmail.getText().toString();

        if(!email.isEmpty()){

            progressBarResetPassword.setVisibility(View.VISIBLE);

            sendEmailToUser(email);

        }else{
            editEmail.requestFocus();
            editEmail.setError("Digite o seu email");
        }

    }
    private void sendEmailToUser(String email){

        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(this, "E-mail enviado com sucesso!", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(this::goToLoginActivity,2500);
            }else{
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            progressBarResetPassword.setVisibility(View.GONE);
        });

    }
    private void configClicks(){

        btnGetBack.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        btnResetPassword.setOnClickListener(view -> {
            validateUserData();
        });

    }
    private void setTextOnToolBar(){

        textOnToolBar.setText("Recuperar senha");

    }
    private void referComponents(){

        btnResetPassword = findViewById(R.id.btn_reset_password);
        textOnToolBar = findViewById(R.id.text_main_toolbar);
        btnGetBack = findViewById(R.id.ib_getback);
        editEmail = findViewById(R.id.edit_email);
        progressBarResetPassword = findViewById(R.id.progress_bar_reset_password);
        btnResetPassword = findViewById(R.id.btn_reset_password);

    }
    public void goToLoginActivity(){
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }


}