package com.example.gestionevenement.entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Date date;
    private Time heure;
    private Time duree;
    private Integer nombreMaxPersonne;

    @ManyToOne
    @JoinColumn(name = "lieu_id")
    private Lieu lieu;


    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDate() {
        return date;
    }

    public Time getDuree() {
        return duree;
    }

    public Integer getNombreMaxPersonne() {
        return nombreMaxPersonne;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public Time getHeure() {
        return heure;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public void setNombreMaxPersonne(Integer nombreMaxPersonne) {
        this.nombreMaxPersonne = nombreMaxPersonne;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }


}
