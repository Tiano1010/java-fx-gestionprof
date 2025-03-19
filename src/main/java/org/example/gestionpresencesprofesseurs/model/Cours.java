package org.example.gestionpresencesprofesseurs.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    //liaison entre cours et salle
    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "professeur_id") //liaison des tables
    private Utilisateur professeur;
    // Getters et Setters
    public  Long getProfesseurId() {
        return professeur.getId();
    }
    public  String getSalleLibelle() {
        return salle.getLibelle();
        }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Utilisateur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Utilisateur professeur) {
        this.professeur = professeur;
    }

    @Override
    public String toString() {
        return
               nom +  "-" +heureDebut +  "H-" +heureFin + "H-" + professeur;
    }
}
