package com.example.casa_por_temporada.Model;

import com.example.casa_por_temporada.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {

    private String name;
    private String email;
    private String phone;
    private String id;
    private String password;

    public void registerUserOnDatabase(){

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("Users")
                .child(FirebaseHelper.getUserIdOnDatabase());
            databaseReference.setValue(this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
