package com.example.casa_por_temporada.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Model.FilterHomes;
import com.example.casa_por_temporada.R;

public class FilterActivity extends AppCompatActivity {

    private TextView textBedroom,textBathroom,textGarage,textTitle;
    private SeekBar sbBedroom,sbBathroom,sbGarage;
    private Button btnClean,btnFilter;
    private ImageButton ibGetBack;
    private int qtt_bedrooms,qtt_bathrooms,qtt_garages;
    private FilterHomes filterHomes;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        referComponents();
        setTextOnToolbar();
        
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            filterHomes = (FilterHomes) bundle.getSerializable("filterHomes");

            if(filterHomes != null){
                setData();
            }
        }
        
        setSeekBar();
        setClicks();


    }
    //--------------------------------------------------------------------------------

    //Setting components with data that came from MainActivity
    private void setData(){

        sbBedroom.setProgress(filterHomes.getQtt_bedrooms());
        sbBathroom.setProgress(filterHomes.getQtt_bathrooms());
        sbGarage.setProgress(filterHomes.getQtt_garages());
        textBedroom.setText(filterHomes.getQtt_bedrooms() + " quartos ou mais");
        textBathroom.setText(filterHomes.getQtt_bathrooms() + " banheiros ou mais");
        textGarage.setText(filterHomes.getQtt_garages() + " garagens ou mais");
        qtt_bedrooms = filterHomes.getQtt_bedrooms();
        qtt_bathrooms = filterHomes.getQtt_bathrooms();
        qtt_garages = filterHomes.getQtt_garages();

    }
    //--------------------------------------------------------------------------------
    
    //Setting clicks on button
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> {finish();});
        btnClean.setOnClickListener(view -> {

            qtt_bedrooms = 0;
            qtt_bathrooms = 0;
            qtt_garages = 0;

            sbBedroom.setProgress(0);
            sbBathroom.setProgress(0);
            sbGarage.setProgress(0);

        });
        btnFilter.setOnClickListener(view -> {

            if(filterHomes == null) filterHomes = new FilterHomes();

            if (qtt_bedrooms > 0) filterHomes.setQtt_bedrooms(qtt_bedrooms);
            if (qtt_bathrooms > 0) filterHomes.setQtt_bathrooms(qtt_bathrooms);
            if (qtt_garages > 0) filterHomes.setQtt_garages(qtt_garages);

            if(qtt_bedrooms > 0 || qtt_bathrooms > 0 || qtt_garages > 0){
                Intent intent = new Intent();
                intent.putExtra("filterHomes", filterHomes);
                setResult(RESULT_OK, intent);
            }

            finish();

        });
    }
    //--------------------------------------------------------------------------------

    //Setting SeekBar
    private void setSeekBar(){

        sbBedroom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textBedroom.setText(progress + " quartos ou mais");
                qtt_bedrooms = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbBathroom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textGarage.setText(progress + " quartos ou mais");
                qtt_bathrooms = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbGarage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textGarage.setText(progress + "quartos ou mais");
                qtt_garages = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    //--------------------------------------------------------------------------------

    //Setting text on toolbar
    private void setTextOnToolbar(){

        textTitle.setText("Filtrar Anúncio");

    }
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        textTitle = findViewById(R.id.text_getback_toolbar);
        textBedroom = findViewById(R.id.text_bedroom);
        textBathroom = findViewById(R.id.text_bathroom);
        textGarage = findViewById(R.id.text_garage);
        sbBedroom = findViewById(R.id.sb_room);
        sbBathroom = findViewById(R.id.sb_bathroom);
        sbGarage = findViewById(R.id.sb_garage);
        btnClean = findViewById(R.id.btn_clean);
        btnFilter = findViewById(R.id.btn_filter);
        ibGetBack = findViewById(R.id.ib_getback);

    }
    //--------------------------------------------------------------------------------
}