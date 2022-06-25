package com.example.casa_por_temporada.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Adapter.AdapterHomes;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyAddsActivity extends AppCompatActivity implements AdapterHomes.OnClick {

    private List<Home> homeList = new ArrayList<>();
    private ProgressBar progressBarMyAddsActivity;
    private TextView textInfo;
    private RecyclerView rvMyAdds;
    private AdapterHomes adapterHomes;
    private ImageButton ibRegisterAdd;


    //Activities' life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adds);
        referComponents();
        setClicks();
        setRecyclerView();
        recoverAddsOnDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recoverAddsOnDatabase();
    }

    //--------------------------------------------------------------------------------


    //Setting recyclerView
    private void setRecyclerView(){
        rvMyAdds.setLayoutManager(new LinearLayoutManager(this));
        rvMyAdds.setHasFixedSize(true);
        adapterHomes = new AdapterHomes(homeList, this);
        rvMyAdds.setAdapter(adapterHomes);
    }
    //--------------------------------------------------------------------------------

    //Recovering adds from Database
    private void recoverAddsOnDatabase(){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("adds")
                .child(FirebaseHelper.getUserIdOnDatabase());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        homeList.clear();
                        textInfo.setVisibility(View.GONE);
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Home home = snap.getValue(Home.class);
                            homeList.add(home);
                        }
                    }else{
                        textInfo.setText("Nenhum anúncio registrado");
                    }

                    progressBarMyAddsActivity.setVisibility(View.GONE);
                    Collections.reverse(homeList);
                    adapterHomes.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MyAddsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }
    //--------------------------------------------------------------------------------

    //Setting clicks on button
    private void setClicks(){

        ibRegisterAdd.setOnClickListener(view -> {

            startActivity(new Intent(this,AdRegistrationActivity.class));

        });

    }
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        textInfo = findViewById(R.id.text_info);
        progressBarMyAddsActivity = findViewById(R.id.progressBarMyAddsActivity);
        rvMyAdds = findViewById(R.id.rv_my_adds);
        ibRegisterAdd = findViewById(R.id.ib_register_add);

    }
    //--------------------------------------------------------------------------------

    //Setting click on item list
    @Override
    public void onClickListener(Home home) {
        Intent intent = new Intent(this,AdRegistrationActivity.class);
        intent.putExtra("home", home);
        startActivity(intent);
    }
    //--------------------------------------------------------------------------------

}