package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.util.List;

public class UtilisateurRepository {
    public Utilisateur getconn(String login, String password) {
        EntityManagerFactory entityManagerFactory= JpaUtil.getEntityManagerFactory();
        Utilisateur utilisateur = entityManagerFactory.createEntityManager().createQuery("SELECT u FROM Utilisateur u WHERE u.email = :login AND u.motDePasse = :password", Utilisateur.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
        return utilisateur;
    }
    public List<Utilisateur> getAllUtilisateur(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Utilisateur> utilisateurs = entityManager.createQuery("from Utilisateur", Utilisateur.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateurs;
    }
    public void addUtilisateur(Utilisateur utilisateur) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(utilisateur);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void deleteUtilisateur(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        entityManager.remove(utilisateur);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void updateUtilisateur(Utilisateur utilisateur){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(utilisateur);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public Utilisateur getUtilisateur(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateur;
    }

    //nombre total d'utilisateur
    public Long countUtilisateur(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Long utilisateur = entityManager.createQuery("select count(u) from Utilisateur u", Long.class).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateur;
    }
    public Long countUtilisateurConducteur(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Long utilisateur = entityManager.createQuery("select count(u) from Utilisateur u where u.role='conducteur'", Long.class).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateur;
    }
    public Long countUtilisateurPassager(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Long utilisateur = entityManager.createQuery("select count(u) from Utilisateur u where u.role='passager'", Long.class).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateur;
    }

    public List<Utilisateur> searchUtilisateur(String search){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Utilisateur> utilisateur = entityManager.createQuery("from Utilisateur u where u.nom like :search or u.prenom like :search or u.email like :search", Utilisateur.class).setParameter("search", "%"+search+"%").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return utilisateur;
    }
}
