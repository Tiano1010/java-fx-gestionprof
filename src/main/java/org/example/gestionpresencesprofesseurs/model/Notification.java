package org.example.gestionpresencesprofesseurs.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//table notifications facultative pour moi risque de surchargé la base de donnée
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime dateEnvoi; //date et heure d’envoie
    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private Utilisateur destinataire;
    // Getters et Setters
}