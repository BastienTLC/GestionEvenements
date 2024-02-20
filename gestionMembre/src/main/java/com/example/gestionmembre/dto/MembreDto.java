package com.example.gestionmembre.dto;

public class MembreDto {

    private Long id;

    private String nom;

    private String prenom;

    private String adresse;

    private Integer age;

    private String mdp;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Integer getAge() {
        return age;
    }

    public String getMdp() {
        return mdp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
