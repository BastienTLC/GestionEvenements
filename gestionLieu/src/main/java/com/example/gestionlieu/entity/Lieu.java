package com.example.gestionlieu.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "lieu")
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String adresse;

    private Integer capacite_accueil;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Integer getCapacite_accueil() {
        return capacite_accueil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCapacite_accueil(Integer capacite_accueil) {
        this.capacite_accueil = capacite_accueil;
    }
}
