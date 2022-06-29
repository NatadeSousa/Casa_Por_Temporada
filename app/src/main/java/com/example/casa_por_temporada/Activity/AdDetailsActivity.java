package com.example.casa_por_temporada.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.Model.User;
import com.example.casa_por_temporada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdDetailsActivity extends AppCompatActivity {

    private ImageView imgHome;
    private TextView textTitle,textDescription,textToolbar;
    private TextView textBedroom,textBathroom,textGarage;
    private Button btnCall;
    private ImageButton ibGetBack;
    private Home home;
    private User user;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);
        referComponents();
        setTextOnToolbar();

        home = (Home) getIntent().getSerializableExtra("home");
        recoverNumber();
        setClicks();
        fillComponents();


    }
    //--------------------------------------------------------------------------------


    //Make a phone call to Home Owner
    private void makePhoneCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + user.getPhone()));
        startActivity(intent);
    }
    //--------------------------------------------------------------------------------

    //Recovering phone number of home owner
    private void recoverNumber(){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("Users")
                .child(home.getUserId());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user = snapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    //

    //Filling components with data that came from list items on MainActivity
    private void fillComponents(){
        if(home != null){
            Picasso.get().load(home.getImageUrl()).into(imgHome);
            textTitle.setText(home.getTitle());
            textDescription.setText(home.getDescription());
            textBedroom.setText(home.getBedroom());
            textBathroom.setText(home.getBathroom());
            textGarage.setText(home.getGarage());
        }else{
            Toast.makeText(this, "Não foi possível recuperar os dados", Toast.LENGTH_SHORT).show();
        }

    }
    //--------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        btnCall.setOnClickListener(view -> {
            if(user != null){
                makePhoneCall();
            }else{
                Toast.makeText(this, "Carregando informações, aguarde...", Toast.LENGTH_SHORT).show();
            }
        });
        ibGetBack.setOnClickListener(view -> {finish();});

    }
    //--------------------------------------------------------------------------------

    //Setting text on toolbar
    private void setTextOnToolbar(){
        textToolbar.setText("Detalhes do Anúncio");
    }
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        imgHome = findViewById(R.id.img_home);
        textToolbar = findViewById(R.id.text_getback_toolbar);
        textTitle = findViewById(R.id.text_title);
        textDescription = findViewById(R.id.text_description);
        textBedroom = findViewById(R.id.text_bedroom);
        textBathroom = findViewById(R.id.text_bathroom);
        textGarage = findViewById(R.id.text_garage);
        btnCall = findViewById(R.id.btn_call);
        ibGetBack = findViewById(R.id.ib_getback);

    }
    //--------------------------------------------------------------------------------



}