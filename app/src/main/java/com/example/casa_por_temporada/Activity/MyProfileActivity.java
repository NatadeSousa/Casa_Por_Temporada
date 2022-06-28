package com.example.casa_por_temporada.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.casa_por_temporada.R;

public class MyProfileActivity extends AppCompatActivity {

    private ImageButton ibSaveProfile;
    private TextView textEmail, textTitle;
    private EditText editName,editPhone;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        setTextOnToolbar();

    }
    //-----------------------------------------------------------------------------------


    //Setting text on toolbar
    private void setTextOnToolbar(){

        textTitle.setText("Meu Perfil");

    }
    //-----------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibSaveProfile = findViewById(R.id.ib_register_add);
        textTitle = findViewById(R.id.text_my_adds_toolbar);
        textEmail = findViewById(R.id.text_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);

    }
    //-----------------------------------------------------------------------------------

}