package com.example.gestionevenement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inscription")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idEvenement;

    private Long IdMembre;

    public Long getId() {
        return id;
    }

    public Long getEvenementId() {
        return idEvenement;
    }

    public Long getMembreId() {
        return IdMembre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEvenementId(Long evenementId) {
        this.idEvenement = evenementId;
    }

    public void setMembreId(Long membreId) {
        this.IdMembre = membreId;
    }
}
