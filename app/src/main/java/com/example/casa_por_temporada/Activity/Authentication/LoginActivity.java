package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Activity.MainActivity;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.R;

public class LoginActivity extends AppCompatActivity {

    private TextView textCreateAccount, textResetPassword,textMainToolbar;
    private EditText editEmail, editPassword;
    private ProgressBar progressBarLogin;
    private Button btnLogin;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        referComponents();
        configClicks();

    }
    //-------------------------------------------------------------------------


    //Setting user login to account
    private void validateUserData(){

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if(!email.isEmpty()){
            if(!password.isEmpty()){

                progressBarLogin.setVisibility(View.VISIBLE);
                signInAccount(email,password);

            }else{
                editPassword.requestFocus();
                editPassword.setError("Digite a sua senha");
            }
        }else{
            editEmail.requestFocus();
            editEmail.setError("Digite o seu e-mail");
        }

    }
    private void signInAccount(String email, String password){

        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email,password
        ).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            progressBarLogin.setVisibility(View.GONE);
        });

    }
    //-------------------------------------------------------------------------

    //Setting the buttons
    private void configClicks(){

        textCreateAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });

        textResetPassword.setOnClickListener(view -> {
            startActivity(new Intent(this,ResetPasswordActivity.class));
        });

        btnLogin.setOnClickListener(view -> {
            validateUserData();
        });

    }
    //-------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        textCreateAccount = findViewById(R.id.text_createAccount);
        textResetPassword = findViewById(R.id.text_resetPassword);
        textMainToolbar = findViewById(R.id.text_getback_toolbar);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);

    }
    //-------------------------------------------------------------------------
}