package org.example.gestionpresencesprofesseurs.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "emargements")
public class Emargement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String statut; // PRÉSENT, ABSENT, RETARD

    @ManyToOne
    @JoinColumn(name = "cours_id") //liaison des tables
    private Cours cours;
    // Getters et Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Utilisateur getCourProfesseur() {
        return cours.getProfesseur();
    }

    public String getCoursNom() {
        return cours.getNom();
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public LocalTime getCoursHeureDebut() {
        return cours.getHeureDebut();
    }
    public LocalTime getCoursHeureFin() {
        return cours.getHeureFin();
    }

}