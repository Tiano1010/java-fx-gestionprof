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
import org.example.gestionpresencesprofesseurs.repository.CoursRepository;
import org.example.gestionpresencesprofesseurs.repository.EmargementRepository;

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
    private TableColumn<Emargement, String> champProf;

    @FXML
    private TableColumn<Emargement, String> champSignature;

    @FXML
    private ComboBox<Cours> comboCours;

    @FXML
    private ComboBox<String> comboStatut;

    @FXML
    private TableView<Emargement> tableEmargement;

    @FXML
    void btnAjout(ActionEvent event) {
        String date = (cDate.getValue() != null) ? cDate.getValue().toString() : null;
        Cours cours = comboCours.getValue();
        String statut = comboStatut.getValue();

        if (date == null || cours == null || statut == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EmargementRepository emargementRepository = new EmargementRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Emargement emargement = new Emargement();
            emargement.setDate(LocalDate.parse(date));
            if (emargement.getDate() != null && emargement.getDate().isEqual(LocalDate.now())) {
                emargement.setStatut(statut);
                emargement.setCours(cours);
                emargementRepository.addEmargement(emargement);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La date est invalide !");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            champFin.setCellValueFactory(new PropertyValueFactory<>("coursHeureFin"));
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

        ObservableList<String> items = FXCollections.observableArrayList("Present", "Absent", "Retard");
        comboStatut.setItems(items);
    }
}
