package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.util.List;

public class ProffesseurRepository {
    public List<Utilisateur> getAllProfesseur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Utilisateur> utilisateurs = entityManager.createQuery("from Utilisateur where role='profeseur'", Utilisateur.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateurs;
    }
}
