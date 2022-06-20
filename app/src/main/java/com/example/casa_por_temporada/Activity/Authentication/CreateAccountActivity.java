package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casa_por_temporada.Model.User;
import com.example.casa_por_temporada.R;

public class CreateAccountActivity extends AppCompatActivity {

    private TextView textMainToolbar;
    private EditText editEmail, editPassword, editPhone, editName;
    private ProgressBar progressBarCreateAccount;
    private Button btnValidateUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        referComponents();
        setTextOnToolBar();
        configClicks();



    }

    private void validateUserData(){

        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String phone = editPhone.getText().toString();

        if(!name.isEmpty()){
            if(!email.isEmpty()){
                if(!password.isEmpty()){
                    if(!phone.isEmpty()){

                        progressBarCreateAccount.setVisibility(View.VISIBLE);
                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setPhone(phone);

                        registerUserOnAuthenticationService(user);

                    }
                }else{
                    editPassword.requestFocus();
                    editPassword.setError("Digite sua senha");
                }
            }else{
                editEmail.requestFocus();
                editEmail.setError("Digite o seu email");
            }
        }else{
            editName.requestFocus();
            editName.setError("Digite o seu nome");
        }

    }
    private void registerUserOnAuthenticationService(User user){



    }

    private void setTextOnToolBar(){

        textMainToolbar.setText("Crie sua conta");

    }
    private void referComponents(){
        textMainToolbar = findViewById(R.id.text_main_toolbar);
        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPassword = findViewById(R.id.edit_password);
        editPhone = findViewById(R.id.edit_phone);
        progressBarCreateAccount = findViewById(R.id.progressBarCreateAccount);
        btnValidateUserData = findViewById(R.id.btn_validateUserData);
    }
    private void configClicks(){

        btnValidateUserData.setOnClickListener(view -> {

            validateUserData();

        });

    }
}