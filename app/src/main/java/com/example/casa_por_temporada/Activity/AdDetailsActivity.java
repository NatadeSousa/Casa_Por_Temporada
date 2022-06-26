package com.example.casa_por_temporada.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.squareup.picasso.Picasso;

public class AdDetailsActivity extends AppCompatActivity {

    private ImageView imgHome;
    private TextView textTitle,textDescription;
    private EditText editBedroom,editBathroom,editGarage;
    private Home home;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);
        referComponents();

        home = (Home) getIntent().getSerializableExtra("home");
        fillComponents();

    }

    //Filling components with data that came from list items on MainActivity
    private void fillComponents(){
        if(home != null){
            Picasso.get().load(home.getImageUrl()).into(imgHome);
            textTitle.setText(home.getTitle());
            textDescription.setText(home.getDescription());
            editBedroom.setText(home.getBedroom());
            editBathroom.setText(home.getBathroom());
            editGarage.setText(home.getGarage());
        }else{
            Toast.makeText(this, "Não foi possível recuperar os dados", Toast.LENGTH_SHORT).show();
        }

    }
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        imgHome = findViewById(R.id.img_home);
        textTitle = findViewById(R.id.text_title);
        textDescription = findViewById(R.id.text_description);
        editBedroom = findViewById(R.id.edit_bedroom);
        editBathroom = findViewById(R.id.edit_bathroom);
        editGarage = findViewById(R.id.edit_garage);

    }
    //--------------------------------------------------------------------------------

}