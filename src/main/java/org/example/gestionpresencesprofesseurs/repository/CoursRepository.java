package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Cours;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.time.LocalTime;
import java.util.List;

public class CoursRepository {

    public boolean isCoursExist(String nom, Salle salle, Utilisateur professeur, LocalTime heureDebut, LocalTime heureFin) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Cours> coursExistants = entityManager.createQuery("SELECT c FROM Cours c WHERE c.nom = :nom AND c.salle = :salle AND c.professeur = :professeur AND c.heureDebut = :heureDebut AND c.heureFin = :heureFin", Cours.class)
                .setParameter("nom", nom)
                .setParameter("salle", salle)
                .setParameter("professeur", professeur)
                .setParameter("heureDebut", heureDebut)
                .setParameter("heureFin", heureFin)
                .getResultList();
        entityManager.close();
        return !coursExistants.isEmpty(); // retourne vrai si le cours existe déjà
    }

    public List<Cours> getAllCours() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Cours> cours = entityManager.createQuery("from Cours", Cours.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return cours;
    }

    public void addCours(Cours cours) {
        if (isCoursExist(cours.getNom(), cours.getSalle(), cours.getProfesseur(), cours.getHeureDebut(), cours.getHeureFin())) {
            throw new IllegalArgumentException("Un cours avec ces détails existe déjà.");
        }

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteCours(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Cours cours = entityManager.find(Cours.class, id);
        entityManager.remove(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateCours(Cours cours) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
