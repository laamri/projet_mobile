
package com.example.lab7_galeriedestars.model;

import java.io.Serializable;

public class Star implements Serializable {

    private int id;
    private String nom;
    private String imageUrl;   // URL distante (Glide)
    private float rating;       // 0.0 a 5.0

    public Star(int id, String nom, String imageUrl, float rating) {
        this.id       = id;
        this.nom      = nom;
        this.imageUrl = imageUrl;
        this.rating   = rating;
    }

    // Getters
    public int    getId()       { return id; }
    public String getNom()      { return nom; }
    public String getImageUrl() { return imageUrl; }
    public float  getRating()   { return rating; }

    // Setter rating (modifie depuis le popup)
    public void setRating(float rating) { this.rating = rating; }
}