package com.example.casa_por_temporada.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
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

import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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

    private Home home;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_registration);
        referComponents();
        setClicks();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            home = (Home) bundle.getSerializable("home");
            fillComponentsToEdit();
        }


    }
    //-----------------------------------------------------------------------------


    //Saving add on Realtime Database and on Storage
    private void saveAddOnDatabases(){
        progressBarAdRegistration.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("adds")
                .child(home.getId() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagePath));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            String imageUrl = task.getResult().toString();
            home.setImageUrl(imageUrl);

            home.saveAddOnRealtimeDatabase();

            finish();

        })).addOnFailureListener(e -> {
            progressBarAdRegistration.setVisibility(View.GONE);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

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
                Toast.makeText(AdRegistrationActivity.this, "Permissão negada", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermission(permissionListener, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE});

    }
    private void showDialogPermission(PermissionListener listener, String[] permissions){

        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissão")
                .setDeniedMessage("Você não concedeu acesso à galeria do seu dispositivo.\n\n Deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Configurações")
                .setPermissions(permissions)
                .check();

    }
    private void openDeviceGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_GALLERY){

                Uri pathSelectedImageOnGallery = data.getData();
                imagePath = pathSelectedImageOnGallery.toString();

                if(Build.VERSION.SDK_INT<28){
                    try{
                        image = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), pathSelectedImageOnGallery);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getBaseContext().getContentResolver(), pathSelectedImageOnGallery);
                    try{
                        image = ImageDecoder.decodeBitmap(source);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                imgHome.setImageBitmap(image);

            }
        }
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

                            if(home == null) home = new Home();
                            home.setUserId(FirebaseHelper.getUserIdOnDatabase());
                            home.setTitle(title);
                            home.setDescription(description);
                            home.setBedroom(bedroom);
                            home.setBathroom(bathroom);
                            home.setGarage(garage);
                            home.setStatus(cbStatus.isChecked());

                            if(imagePath != null){
                                saveAddOnDatabases();
                            }else if(home.getImageUrl() != null){
                                home.saveAddOnRealtimeDatabase();
                                finish();
                            }else{
                                Toast.makeText(this, "Selecione uma imagem para o anúncio!", Toast.LENGTH_SHORT).show();
                            }

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

    //Getting back all the components from MyAddsActivity
    private void fillComponentsToEdit(){

        Picasso.get().load(home.getImageUrl()).into(imgHome);
        editTitle.setText(home.getTitle());
        editDescription.setText(home.getDescription());
        editBedroom.setText(home.getBedroom());
        editBathroom.setText(home.getBathroom());
        editGarage.setText(home.getGarage());
        cbStatus.setChecked(home.isStatus());

    }
    //-----------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> startActivity(new Intent(this,MainActivity.class)));
        ibSaveAdd.setOnClickListener(view -> validateData());

    }
    //-----------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        toolbarTitle = findViewById(R.id.text_registration_toolbar);
        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        editBedroom = findViewById(R.id.text_bedroom);
        editBathroom = findViewById(R.id.text_bathroom);
        editGarage = findViewById(R.id.text_garage);
        cbStatus = findViewById(R.id.cb_status);
        ibGetBack = findViewById(R.id.ib_getback);
        ibSaveAdd = findViewById(R.id.ib_save_add);
        progressBarAdRegistration = findViewById(R.id.progress_bar_add_registration);
        imgHome = findViewById(R.id.img_home);

    }
    //-----------------------------------------------------------------------------

}