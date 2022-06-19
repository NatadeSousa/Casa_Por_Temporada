package com.example.casa_por_temporada.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Activity.MainActivity;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.User;
import com.example.casa_por_temporada.R;

public class CreateAccountActivity extends AppCompatActivity {

    private TextView textMainToolbar;
    private EditText editEmail, editPassword, editPhone, editName;
    private ProgressBar progressBarCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        referComponents();
        setTextOnToolBar();
        validateUserData();


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
                        registerUserOnAuthenticator(user);


                    }else{
                        editPhone.requestFocus();
                        editPhone.setError("Digite o número do seu telefone");
                    }
                }else{
                    editPassword.requestFocus();
                    editPassword.setError("Digite a sua senha");
                }
            }else{
                editEmail.requestFocus();
                editEmail.setError("Digite o seu e-mail");
            }
        }else{
            editName.requestFocus();
            editName.setError("Digite o seu nome");
        }

    }
    private void registerUserOnAuthenticator(User user){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String idUser = task.getResult().getUser().getUid();
                user.setId(idUser);
                user.saveUserOnDatabase();

                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
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
    }
}