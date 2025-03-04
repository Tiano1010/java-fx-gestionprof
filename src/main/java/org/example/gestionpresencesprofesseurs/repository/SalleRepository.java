package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.util.List;

public class SalleRepository {

    public void addSalle(Salle salle) {
        //recuperant les informations de l'utilisateur connecté
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(salle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteSalle(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Salle salle = entityManager.find(Salle.class, id);
        entityManager.remove(salle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateSalle(Salle salle){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(salle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Salle> getAllSalle(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Salle> salles = entityManager.createQuery("from Salle", Salle.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return salles;
    }
}
