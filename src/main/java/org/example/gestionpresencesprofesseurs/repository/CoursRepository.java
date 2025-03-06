package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Cours;
import org.example.gestionpresencesprofesseurs.model.Salle;

import java.util.List;

public class CoursRepository {

    public List<Cours> getAllCours(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Cours> cours = entityManager.createQuery("from Cours", Cours.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return cours;
    }

    public void addCours(Cours cours) {
        //recuperant les informations de l'utilisateur connecté
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void deleteCours(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Cours cours = entityManager.find(Cours.class, id);
        entityManager.remove(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateCours(Cours cours){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(cours);
        entityManager.getTransaction().commit();
        entityManager.close();
    }







}
