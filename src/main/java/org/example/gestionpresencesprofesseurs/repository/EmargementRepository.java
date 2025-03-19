package org.example.gestionpresencesprofesseurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.JpaUtil;
import org.example.gestionpresencesprofesseurs.model.Cours;
import org.example.gestionpresencesprofesseurs.model.Emargement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmargementRepository {

    public void addEmargement(Emargement emargement) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emargement);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public List<Emargement> getAllEmargements() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Emargement> emargements = entityManager.createQuery("from Emargement  ", Emargement.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return emargements;    }

    public void updateEmargement(Emargement emargement) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(emargement);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deleteEmargement(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Emargement emargement = entityManager.find(Emargement.class, id);
            if (emargement != null) {
                entityManager.remove(emargement);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Emargement getEmargementById(Long id) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Emargement emargement = entityManager.find(Emargement.class, id);
        entityManager.close();
        return emargement;
    }

    public Map<String, Long> getEmargementsParProfesseur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            List<Object[]> results = entityManager.createQuery(
                            "SELECT e.cours.professeur.nom, COUNT(e) " +
                                    "FROM Emargement e " +
                                    "GROUP BY e.cours.professeur.nom", Object[].class)
                    .getResultList();
            Map<String, Long> emargementsParProf = new HashMap<>();
            for (Object[] result : results) {
                emargementsParProf.put((String) result[0], (Long) result[1]);
            }

            return emargementsParProf;
        } finally {
            entityManager.close();
        }
    }

    public Map<String, Double> getTauxPresenceParCours() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            List<Object[]> results = entityManager.createQuery(
                            "SELECT e.cours.nom, " +
                                    "       SUM(CASE WHEN e.statut = 'Present' THEN 1 ELSE 0 END), " +
                                    "       COUNT(e) " +
                                    "FROM Emargement e " +
                                    "GROUP BY e.cours.nom", Object[].class)
                    .getResultList();

            Map<String, Double> tauxPresenceParCours = new HashMap<>();
            for (Object[] result : results) {
                String coursNom = (String) result[0];
                Long totalPresences = (Long) result[1];
                Long totalEmargements = (Long) result[2];
                double tauxPresence = (totalEmargements > 0) ? (totalPresences * 100.0) / totalEmargements : 0.0;
                tauxPresenceParCours.put(coursNom, tauxPresence);
            }

            return tauxPresenceParCours;
        } finally {
            entityManager.close();
        }
    }
//
//    public Map<LocalDate, Long> getEmargementsParDate(String interval) {
//        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        String queryString = "";
//        if ("jour".equals(interval)) {
//            queryString = "SELECT e.date, COUNT(e) FROM Emargement e GROUP BY e.date ORDER BY e.date";
//        } else if ("semaine".equals(interval)) {
//            queryString = "SELECT FUNCTION('YEAR', e.date), FUNCTION('WEEK', e.date), COUNT(e) FROM Emargement e GROUP BY FUNCTION('YEAR', e.date), FUNCTION('WEEK', e.date) ORDER BY FUNCTION('YEAR', e.date), FUNCTION('WEEK', e.date)";
//        } else if ("mois".equals(interval)) {
//            queryString = "SELECT FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date), COUNT(e) FROM Emargement e GROUP BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date) ORDER BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date)";
//        }
//
//        try {
//            List<Object[]> results = entityManager.createQuery(queryString, Object[].class).getResultList();
//            Map<LocalDate, Long> emargementsParDate = new LinkedHashMap<>();
//            for (Object[] result : results) {
//                if ("semaine".equals(interval)) {
//                    LocalDate date = LocalDate.of((Integer) result[0], 1, 1).plusWeeks((Integer) result[1] - 1); // Calculer la date de début de semaine
//                    emargementsParDate.put(date, (Long) result[2]);
//                } else if ("mois".equals(interval)) {
//                    LocalDate date = LocalDate.of((Integer) result[0], (Integer) result[1], 1); // Calculer le premier jour du mois
//                    emargementsParDate.put(date, (Long) result[2]);
//                } else {
//                    emargementsParDate.put((LocalDate) result[0], (Long) result[1]);
//                }
//            }
//
//            return emargementsParDate;
//        } finally {
//            entityManager.close();
//        }
//    }

}
