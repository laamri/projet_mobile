package com.example.projetws.beans;



public class Etudiant {
    private int id;
    private String nom, prenom, ville, sexe;

    public Etudiant() {}

    public Etudiant(String nom, String prenom, String ville, String sexe) {
        this.nom    = nom;
        this.prenom = prenom;
        this.ville  = ville;
        this.sexe   = sexe;
    }

    // Getters
    public int    getId()     { return id; }
    public String getNom()    { return nom; }
    public String getPrenom() { return prenom; }
    public String getVille()  { return ville; }
    public String getSexe()   { return sexe; }

    @Override
    public String toString() {
        return nom + " " + prenom + " — " + ville + " (" + sexe + ")";
    }
}