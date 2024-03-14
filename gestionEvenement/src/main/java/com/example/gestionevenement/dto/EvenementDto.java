package com.example.gestionevenement.dto;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class EvenementDto {
    private Long id;
    private String nom;
    private Date dateEvenement;
    private LocalTime heure;
    private Long duree;
    private Long lieuId;
    private Integer nombreMaxPersonnes;

    // Constructeurs, getters et setters

    public EvenementDto() {
    }

    public EvenementDto(Long id, String nom, Date dateEvenement, LocalTime heure, Long duree, Long lieuId, Integer nombreMaxPersonnes) {
        this.id = id;
        this.nom = nom;
        this.dateEvenement = dateEvenement;
        this.heure = heure;
        this.duree = duree;
        this.lieuId = lieuId;
        this.nombreMaxPersonnes = nombreMaxPersonnes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Long getLieuId() {
        return lieuId;
    }

    public void setLieuId(Long lieuId) {
        this.lieuId = lieuId;
    }

    public Integer getNombreMaxPersonnes() {
        return nombreMaxPersonnes;
    }

    public void setNombreMaxPersonnes(Integer nombreMaxPersonnes) {
        this.nombreMaxPersonnes = nombreMaxPersonnes;
    }

}
