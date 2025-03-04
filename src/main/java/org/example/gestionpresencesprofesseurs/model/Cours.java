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
}