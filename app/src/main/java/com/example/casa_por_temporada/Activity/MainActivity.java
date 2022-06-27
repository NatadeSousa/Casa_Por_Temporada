package com.example.casa_por_temporada.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Activity.Authentication.LoginActivity;
import com.example.casa_por_temporada.Adapter.AdapterHomes;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterHomes.OnClick {

    private RecyclerView rvAllAdds;
    private AdapterHomes adapterHomes;
    private List<Home> homeList = new ArrayList<>();
    private ImageButton ibMore;
    private ProgressBar progressBarMainActivity;
    private TextView textInfo;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        referComponents();
        setClicks();



    }

    @Override
    protected void onStart() {
        super.onStart();
        setAdapterHomes();
        recoverAdds();
    }

    //--------------------------------------------------------------------------------


    //Recovering all adds from database
    private void recoverAdds(){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("public_adds");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        textInfo.setText("Nenhum anúncio foi registrado");
                    }
                    progressBarMainActivity.setVisibility(View.GONE);
                    Collections.reverse(homeList);
                    adapterHomes.notifyDataSetChanged();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    //--------------------------------------------------------------------------------

    //Setting adapter
    private void setAdapterHomes(){
        rvAllAdds.setLayoutManager(new LinearLayoutManager(this));
        rvAllAdds.setHasFixedSize(true);
        adapterHomes = new AdapterHomes(homeList,this);
        rvAllAdds.setAdapter(adapterHomes);
    }
    //--------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibMore.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(this,ibMore);
            popupMenu.getMenuInflater().inflate(R.menu.menu_main_activity,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {

                if(menuItem.getItemId() == R.id.item_filter){
                    startActivity(new Intent(this,FilterActivity.class));
                }else if(menuItem.getItemId() == R.id.item_adds){
                    if(FirebaseHelper.isUserAuthenticated()) {
                        startActivity(new Intent(this,MyAddsActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }else{
                    if(FirebaseHelper.isUserAuthenticated()){
                        startActivity(new Intent(this,MyProfileActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }
                return true;
            });
            popupMenu.show();
        });

    }
    //--------------------------------------------------------------------------------

    //Showing text dialog whether user isn't logged in his account
    private void showDialogLogin(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Entre na sua conta");
        builder.setMessage("Você não está conectado na sua conta. Deseja realizar o login?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog,which) -> dialog.dismiss());
        builder.setPositiveButton("Sim", (dialog,which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibMore = findViewById(R.id.ib_more);
        textInfo = findViewById(R.id.text_info);
        progressBarMainActivity = findViewById(R.id.progressBarMyAddsActivity);
        rvAllAdds = findViewById(R.id.rv_all_adds);

    }
    //--------------------------------------------------------------------------------

    //Setting clicks on list items
    @Override
    public void onClickListener(Home home) {

        Intent intent = new Intent(this,AdDetailsActivity.class);
        intent.putExtra("home", home);
        startActivity(intent);

    }
    //--------------------------------------------------------------------------------
}