package com.example.gestionevenement.entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "evenements")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_evenement", nullable = false)
    private Date dateEvenement;

    @Column(name = "heure", nullable = false)
    private LocalTime heure;

    @Column(name = "duree", nullable = false)
    private Long duree;

    @Column(name = "lieu_id", nullable = false)
    private Long lieuId;

    @Column(name = "nombre_max_personnes", nullable = false)
    private Integer nombreMaxPersonnes;

    // Constructeurs, getters et setters

    // Constructor
    public Evenement() {}

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
