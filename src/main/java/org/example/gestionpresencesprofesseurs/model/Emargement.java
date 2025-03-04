package org.example.gestionpresencesprofesseurs.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "emargements")
public class Emargement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String statut; // PRÉSENT, ABSENT, RETARD
    @ManyToOne
    @JoinColumn(name = "professeur_id") //liaison des tables
    private Utilisateur professeur;
    @ManyToOne
    @JoinColumn(name = "cours_id") //liaison des tables
    private Cours cours;
    // Getters et Setters
}