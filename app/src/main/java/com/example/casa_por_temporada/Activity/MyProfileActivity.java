package com.example.casa_por_temporada.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casa_por_temporada.Activity.Authentication.LoginActivity;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.User;
import com.example.casa_por_temporada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {

    private ImageButton ibSaveProfile,ibGetBack;
    private Button btnLogout;
    private TextView  textTitle;
    private EditText editName,editPhone, editEmail;
    private ProgressBar progressBarMyProfile;
    private User user;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        setTextOnToolbar();
        recoverData();
        setClicks();

    }
    //-----------------------------------------------------------------------------------

    //Validating data
    private void validateData(){

        String name = editName.getText().toString();
        String phone = editPhone.getText().toString();

        if(!name.isEmpty()){
            if(!phone.isEmpty()){

                progressBarMyProfile.setVisibility(View.VISIBLE);

                user.setName(name);
                user.setPhone(phone);
                user.registerUserOnDatabase();

                progressBarMyProfile.setVisibility(View.GONE);
                finish();

            }else{
                editPhone.requestFocus();
                editPhone.setError("Digite o seu número de telefone");
            }
        }else{
            editName.requestFocus();
            editName.setError("Digite o seu nome");
        }

    }
    //-----------------------------------------------------------------------------------

    //Recovering user data from Database
    private void recoverData(){

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("Users")
                .child(FirebaseHelper.getUserIdOnDatabase());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);

                fillComponents();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //-----------------------------------------------------------------------------------

    //Filling components with data that came from Database
    private void fillComponents(){
        editName.setText(user.getName());
        editPhone.setText(user.getPhone());
        editEmail.setText(user.getEmail());

        progressBarMyProfile.setVisibility(View.GONE);
    }
    //-----------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> {finish();});
        ibSaveProfile.setOnClickListener(view -> {validateData();});
        btnLogout.setOnClickListener(view -> {

            FirebaseHelper.getAuth().signOut();

            finish();
            startActivity(new Intent(this, LoginActivity.class));


        });

    }
    //-----------------------------------------------------------------------------------

    //Setting text on toolbar
    private void setTextOnToolbar(){

        textTitle.setText("Meu Perfil");

    }
    //-----------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibSaveProfile = findViewById(R.id.ib_save_add);
        ibGetBack = findViewById(R.id.ib_getback);
        btnLogout = findViewById(R.id.btn_logout);
        textTitle = findViewById(R.id.text_registration_toolbar);
        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        progressBarMyProfile = findViewById(R.id.progress_bar_my_profile);


    }
    //-----------------------------------------------------------------------------------

}