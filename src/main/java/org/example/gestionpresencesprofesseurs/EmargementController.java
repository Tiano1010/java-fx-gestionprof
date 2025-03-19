package org.example.gestionpresencesprofesseurs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.gestionpresencesprofesseurs.model.Cours;
import org.example.gestionpresencesprofesseurs.model.Emargement;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.CoursRepository;
import org.example.gestionpresencesprofesseurs.repository.EmargementRepository;
import org.example.gestionpresencesprofesseurs.repository.SalleRepository;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EmargementController implements Initializable {

    @FXML
    private DatePicker cDate;

    @FXML
    private TableColumn<Emargement, String> champCours;

    @FXML
    private TableColumn<Emargement, String> champDate;

    @FXML
    private TableColumn<Emargement, String> champDebut;

    @FXML
    private TableColumn<Emargement, String> champFin;

    @FXML
    private TableColumn<Emargement, String> champNumero;

    @FXML
    private TableColumn<Emargement, String>champProf;

    @FXML
    private TableColumn<Emargement, String> champSignature;

    @FXML
    private ComboBox<Cours> comboCours;

    @FXML
    private ComboBox<?> comboStatut;

    @FXML
    private TableView<Emargement> tableEmargement;

    @FXML
    void btnAjout(ActionEvent event) {

        String date = cDate.getValue().toString();
        Cours cours = comboCours.getValue();
        String statut = comboStatut.getValue().toString();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EmargementRepository emargementRepository = new EmargementRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Emargement emargement = new Emargement();
            emargement.setDate(LocalDate.parse(date));
            if (emargement.getDate() != null && emargement.getDate().isEqual(LocalDate.now())){
                // emargement.setDate(LocalDate.parse(date));
                emargement.setStatut(statut);
                emargement.setCours(cours);
                emargementRepository.addEmargement(emargement);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("la date est invalide !.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
        afficherEmargement();
        cDate.setValue(null);
        comboCours.setValue(null);
        comboStatut.setValue(null);
    }

    public void afficherEmargement() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EmargementRepository emargementRepository = new EmargementRepository();
        try {
            List<Emargement> emargements = emargementRepository.getAllEmargements();
            ObservableList<Emargement> res = FXCollections.observableArrayList(emargements);
            champNumero.setCellValueFactory(new PropertyValueFactory<>("id"));
            champCours.setCellValueFactory(new PropertyValueFactory<>("coursNom"));
            champProf.setCellValueFactory(new PropertyValueFactory<>("CourProfesseur"));
            champDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            champDebut.setCellValueFactory(new PropertyValueFactory<>("coursHeureDebut"));
            champFin.setCellValueFactory(new PropertyValueFactory<>("coursHeureDebut"));
            champSignature.setCellValueFactory(new PropertyValueFactory<>("statut"));
            tableEmargement.setItems(res);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherEmargement();
        CoursRepository coursRepository = new CoursRepository();
        List<Cours> coursList = coursRepository.getAllCours();
        comboCours.setItems(FXCollections.observableArrayList(coursList));


        ObservableList items = comboStatut.getItems();
        items.add("Present");
        items.add("Absent");
        items.add("Retard");
    }
}
