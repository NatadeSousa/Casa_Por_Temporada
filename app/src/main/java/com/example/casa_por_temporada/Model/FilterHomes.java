package com.example.casa_por_temporada.Model;

import java.io.Serializable;

public class FilterHomes implements Serializable {

    private int qtt_bedrooms;
    private int qtt_bathrooms;
    private int qtt_garages;

    public int getQtt_bedrooms() {
        return qtt_bedrooms;
    }

    public void setQtt_bedrooms(int qtt_bedrooms) {
        this.qtt_bedrooms = qtt_bedrooms;
    }

    public int getQtt_bathrooms() {
        return qtt_bathrooms;
    }

    public void setQtt_bathrooms(int qtt_bathrooms) {
        this.qtt_bathrooms = qtt_bathrooms;
    }

    public int getQtt_garages() {
        return qtt_garages;
    }

    public void setQtt_garages(int qtt_garages) {
        this.qtt_garages = qtt_garages;
    }
}
