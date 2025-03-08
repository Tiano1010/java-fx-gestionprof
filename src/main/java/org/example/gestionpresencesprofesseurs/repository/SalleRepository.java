package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Salle;

import java.util.List;

public class SalleRepository {

    // Méthode pour vérifier si une salle existe déjà par son libellé
    public boolean isSalleExist(String libelle) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Salle> sallesExistantes = entityManager.createQuery("SELECT s FROM Salle s WHERE s.libelle = :libelle", Salle.class)
                .setParameter("libelle", libelle)
                .getResultList();
        entityManager.close();
        return !sallesExistantes.isEmpty(); // retourne vrai si la salle existe déjà
    }

    // Ajout d'une nouvelle salle après vérification des doublons
    public void addSalle(Salle salle) {
        if (isSalleExist(salle.getLibelle())) {
            throw new IllegalArgumentException("Une salle avec ce libellé existe déjà.");
        }

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(salle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Suppression d'une salle par son id
    public void deleteSalle(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Salle salle = entityManager.find(Salle.class, id);
        if (salle != null) {
            entityManager.remove(salle);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Mise à jour d'une salle
    public void updateSalle(Salle salle) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(salle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Récupération de toutes les salles
    public List<Salle> getAllSalle() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Salle> salles = entityManager.createQuery("from Salle", Salle.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return salles;
    }
}
