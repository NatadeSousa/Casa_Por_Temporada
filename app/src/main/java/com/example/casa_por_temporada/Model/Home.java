package com.example.casa_por_temporada.Model;

import android.widget.Toast;

import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Home implements Serializable {

    private String id;
    private String title;
    private String description;
    private String bedroom;
    private String bathroom;
    private String garage;
    private boolean status;
    private String imageUrl;

    public Home() {

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        this.setId(databaseReference.push().getKey());

    }

    public void saveAddOnRealtimeDatabase() {

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("adds")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .child(this.getId());
        databaseReference.setValue(this);


    }

    public void deleteAddOnDatabases() {

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("adds")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .child(this.getId());
            databaseReference.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    StorageReference storageReference = FirebaseHelper.getStorageReference()
                            .child("images")
                            .child("adds")
                            .child(this.getId() + ".jpeg");
                        storageReference.delete();
                }
            });

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
