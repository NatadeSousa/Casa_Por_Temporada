package com.example.casa_por_temporada.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class AdRegistrationActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 100;

    private TextView toolbarTitle;
    private EditText editTitle,editDescription,editBedroom,editBathroom,editGarage;
    private CheckBox cbStatus;
    private ImageButton ibSaveAdd,ibGetBack;
    private ProgressBar progressBarAdRegistration;

    private ImageView imgHome;
    private String imagePath;
    private Bitmap image;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_registration);
        referComponents();
        configClicks();
        setToolbarTitle();


    }
    //-----------------------------------------------------------------------------


    //Setting home photo
    public void verifyUserPermission(View view) {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openDeviceGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AdRegistrationActivity.this, "Permissão Negada", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissionGallery(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});

    }
    private void showDialogPermissionGallery(PermissionListener listener, String[] permissions){

        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissões")
                .setDeniedMessage("Você não permitiu o acesso à galeria do dispositivo.\n\nDeseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Configurações")
                .setPermissions(permissions)
                .check();

    }
    private void openDeviceGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_GALLERY);

    }
    //-----------------------------------------------------------------------------

    //Validating data about the add
    private void validateData(){

        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        String bedroom = editBedroom.getText().toString();
        String bathroom = editBathroom.getText().toString();
        String garage = editGarage.getText().toString();

        if(!title.isEmpty()){
            if(!description.isEmpty()){
                if(!bedroom.isEmpty()){
                    if(!bathroom.isEmpty()){
                        if(!garage.isEmpty()){

                            progressBarAdRegistration.setVisibility(View.VISIBLE);

                            Home home = new Home();
                            home.setTitle(title);
                            home.setDescription(description);
                            home.setBedroom(bedroom);
                            home.setBathroom(bathroom);
                            home.setGarage(garage);
                            home.setStatus(cbStatus.isChecked());

                            Toast.makeText(this, "Alright", Toast.LENGTH_SHORT).show();

                        }else{
                            editGarage.requestFocus();
                            editGarage.setError("Campo obrigatório!");
                        }
                    }else{
                        editBathroom.requestFocus();
                        editBathroom.setError("Campo obrigatório!");
                    }
                }else{
                    editBedroom.requestFocus();
                    editBedroom.setError("Campo obrigatório!");
                }
            }else{
                editDescription.requestFocus();
                editDescription.setError("Campo obrigatório!");
            }
        }else{
            editTitle.requestFocus();
            editTitle.setError("Campo obrigatório!");
        }

    }
    //-----------------------------------------------------------------------------

    //Setting clicks on buttons
    private void configClicks(){

        ibGetBack.setOnClickListener(view -> startActivity(new Intent(this,MainActivity.class)));
        ibSaveAdd.setOnClickListener(view -> validateData());

    }
    //-----------------------------------------------------------------------------

    //Setting title on toolbar
    private void setToolbarTitle(){

        toolbarTitle.setText("Novo Anúncio");

    }
    //-----------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        toolbarTitle = findViewById(R.id.text_registration_toolbar);
        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        editBedroom = findViewById(R.id.edit_bedroom);
        editBathroom = findViewById(R.id.edit_bathroom);
        editGarage = findViewById(R.id.edit_garage);
        cbStatus = findViewById(R.id.cb_status);
        ibGetBack = findViewById(R.id.ib_getback);
        ibSaveAdd = findViewById(R.id.ib_save_add);
        progressBarAdRegistration = findViewById(R.id.progress_bar_add_registration);
        imgHome = findViewById(R.id.img_home);

    }
    //-----------------------------------------------------------------------------

}