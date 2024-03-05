package com.example.gestionevenement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inscriptions")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evenement_id")
    private Long idEvenement;
    @Column(name = "membre_id")
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
