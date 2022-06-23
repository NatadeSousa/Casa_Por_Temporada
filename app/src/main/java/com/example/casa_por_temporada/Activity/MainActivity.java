package com.example.casa_por_temporada.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.casa_por_temporada.Activity.Authentication.LoginActivity;
import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        referComponents();
        configClicks();


    }


    private void configClicks(){

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

    private void referComponents(){

        ibMore = findViewById(R.id.ib_more);

    }
}