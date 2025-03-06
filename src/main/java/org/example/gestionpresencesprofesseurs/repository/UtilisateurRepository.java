package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.util.List;

public class UtilisateurRepository {

    public Utilisateur getconn(String login, String password) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Utilisateur utilisateur = null;

        try {
            utilisateur = entityManager.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.email = :login AND u.motDePasse = :password",
                            Utilisateur.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Aucun utilisateur trouvé
            System.out.println("Aucun utilisateur trouvé avec ces identifiants.");
        } finally {
            entityManager.close();
        }

        return utilisateur;
    }

    public List<Utilisateur> getAllUtilisateur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Utilisateur> utilisateurs = entityManager.createQuery("from Utilisateur", Utilisateur.class).getResultList();
        entityManager.close();
        return utilisateurs;
    }

    public void addUtilisateur(Utilisateur utilisateur) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(utilisateur);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deleteUtilisateur(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            if (utilisateur != null) {
                entityManager.remove(utilisateur);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(utilisateur);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Utilisateur getUtilisateur(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        entityManager.close();
        return utilisateur;
    }

    public Long countUtilisateur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM Utilisateur u", Long.class).getSingleResult();
        entityManager.close();
        return count;
    }

    public Long countUtilisateurConducteur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.role = 'conducteur'", Long.class).getSingleResult();
        entityManager.close();
        return count;
    }

    public Long countUtilisateurPassager() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.role = 'passager'", Long.class).getSingleResult();
        entityManager.close();
        return count;
    }

    public List<Utilisateur> searchUtilisateur(String search) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Utilisateur> utilisateurs = entityManager.createQuery(
                        "FROM Utilisateur u WHERE LOWER(u.nom) LIKE LOWER(:search) OR LOWER(u.prenom) LIKE LOWER(:search) OR LOWER(u.email) LIKE LOWER(:search)",
                        Utilisateur.class)
                .setParameter("search", "%" + search + "%")
                .getResultList();

        entityManager.close();
        return utilisateurs;
    }
}
