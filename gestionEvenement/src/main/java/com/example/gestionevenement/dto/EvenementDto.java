package com.example.gestionevenement.dto;

import java.sql.Time;
import java.util.Date;

public class EvenementDto {
    private Long id;
    private String nom;

    private Date date;

    private Time duree;
    private Integer nombreMaxPersonne;
    private Long idLieu;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Time getDuree() {
        return duree;
    }

    public Integer getNombreMaxPersonne() {
        return nombreMaxPersonne;
    }

    public Date getDate() {
        return date;
    }


    public long getIdLieu() {
        return idLieu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public void setNombreMaxPersonne(Integer nombreMaxPersonne) {
        this.nombreMaxPersonne = nombreMaxPersonne;
    }

    public void setIdLieu(Long  idLieu) {
        this.idLieu = idLieu;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
