package com.example.gestionevenement.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "lieu")
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String adresse;
    private Integer capaciteAccueil;

    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Integer getCapaciteAccueil() {
        return capaciteAccueil;
    }

    // Getters and setters
    // You can generate them using your IDE or write them manually
}
