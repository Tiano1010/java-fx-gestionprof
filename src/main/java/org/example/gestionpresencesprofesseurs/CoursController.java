package org.example.gestionpresencesprofesseurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.gestionpresencesprofesseurs.model.Cours;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.CoursRepository;
import org.example.gestionpresencesprofesseurs.repository.SalleRepository;
import org.example.gestionpresencesprofesseurs.repository.UtilisateurRepository;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class CoursController implements Initializable {

    @FXML private ComboBox<Salle> comboSalle;
    @FXML private ComboBox<Utilisateur> comboProfesseur;
    @FXML private TextField champNom;
    @FXML private TextField champDescription;
    @FXML private TextField champDebut;
    @FXML private TextField champFin;
    @FXML private TableView<Cours> tableCours;
    @FXML private TableColumn<Cours, Long> colNumero;
    @FXML private TableColumn<Cours, String> colNom;
    @FXML private TableColumn<Cours, String> colDescription;
    @FXML private TableColumn<Cours, String> colDebut;
    @FXML private TableColumn<Cours, String> colFin;
    @FXML private TableColumn<Cours, String> colSalle;
    @FXML private TableColumn<Cours, String> colProfesseur;

    private final CoursRepository coursRepository = new CoursRepository();

    @FXML
    void btnAjouter(ActionEvent event) {
        try {
            String nom = champNom.getText().trim();
            String description = champDescription.getText().trim();
            LocalTime heureDebut = LocalTime.parse(champDebut.getText().trim());
            LocalTime heureFin = LocalTime.parse(champFin.getText().trim());
            Salle salle = comboSalle.getValue();
            Utilisateur professeur = comboProfesseur.getValue();

            if (nom.isEmpty() || description.isEmpty() || salle == null || professeur == null) {
                showAlert(Alert.AlertType.WARNING, "Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            Cours cours = new Cours();
            cours.setNom(nom);
            cours.setDescription(description);
            cours.setHeureDebut(heureDebut);
            cours.setHeureFin(heureFin);
            cours.setSalle(salle);
            cours.setProfesseur(professeur);

            // Enregistrement du cours
            try {
                coursRepository.addCours(cours);

                // Envoi de mail au professeur
                String recipient = professeur.getEmail();
                String subject = "Attribution de cours";
                String content = "Bonjour " + professeur.getPrenom() + " " + professeur.getNom() + ",\n\n" +
                        "Vous avez un cours:\n" +
                        "Matière: " + cours.getNom() + "\n" +
                        "Heure: " + cours.getHeureDebut() + " - " + cours.getHeureFin() + "\n" +
                        "Salle: " + salle + "\n\n" +
                        "Merci, bonne réception.";
                EmailSender.sendEmail(recipient, subject, content);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Cours ajouté avec succès.");
                btnAnnuler(event);
                afficherCours();
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.WARNING, "Doublon", e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du cours.");
        }
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        Cours selectedCours = tableCours.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            try {
                selectedCours.setNom(champNom.getText().trim());
                selectedCours.setDescription(champDescription.getText().trim());
                selectedCours.setHeureDebut(LocalTime.parse(champDebut.getText().trim()));
                selectedCours.setHeureFin(LocalTime.parse(champFin.getText().trim()));
                selectedCours.setSalle(comboSalle.getValue());
                selectedCours.setProfesseur(comboProfesseur.getValue());

                coursRepository.updateCours(selectedCours);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Cours mis à jour.");
                afficherCours();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Sélectionnez un cours à modifier.");
        }
    }

    @FXML
    void btnSupprimer(ActionEvent event) {
        Cours selectedCours = tableCours.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            try {
                coursRepository.deleteCours(selectedCours.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Cours supprimé.");
                afficherCours();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le cours.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Sélectionnez un cours à supprimer.");
        }
    }

    @FXML
    void btnAnnuler(ActionEvent event) {
        champNom.clear();
        champDescription.clear();
        champDebut.clear();
        champFin.clear();
        comboSalle.setValue(null);
        comboProfesseur.setValue(null);
    }

    private void afficherCours() {
        try {
            List<Cours> coursList = coursRepository.getAllCours();
            ObservableList<Cours> res = FXCollections.observableArrayList(coursList);

            colNumero.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colDebut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
            colFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colSalle.setCellValueFactory(new PropertyValueFactory<>("salleLibelle"));
            colProfesseur.setCellValueFactory(new PropertyValueFactory<>("professeur"));

            tableCours.setItems(res);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les cours.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Remplir comboBox des salles
        SalleRepository salleRepository = new SalleRepository();
        List<Salle> salleList = salleRepository.getAllSalle();
        comboSalle.setItems(FXCollections.observableArrayList(salleList));

        // Remplir comboBox des professeurs
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        List<Utilisateur> professeursList = utilisateurRepository.getAllUtilisateur();
        ObservableList<Utilisateur> professeursObservableList = FXCollections.observableArrayList(professeursList);

        comboProfesseur.setCellFactory(param -> new ListCell<Utilisateur>() {
            @Override
            protected void updateItem(Utilisateur item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.getNom() + " " + item.getPrenom());
            }
        });

        comboProfesseur.setItems(professeursObservableList);

        // Affichage initial des cours
        afficherCours();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
