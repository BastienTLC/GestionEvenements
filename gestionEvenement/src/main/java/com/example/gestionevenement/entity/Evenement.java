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
    private Time duree;
    private Integer nombreMaxPersonne;

    private long lieuId;


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

    public Long getLieuId() {
        return lieuId;
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

    public void setLieuId(Long lieuId) {
        this.lieuId = lieuId;
    }



}
