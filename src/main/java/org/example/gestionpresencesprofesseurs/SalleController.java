package org.example.gestionpresencesprofesseurs;

import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.SalleRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalleController implements Initializable {

    @FXML
    private TextField champLibelle;

    @FXML
    private TableColumn<Salle, Long> colId;

    @FXML
    private TableColumn<Salle, String> colLibelle;

    @FXML
    private TableView<Salle> table;

    private final SalleRepository salleRepository = new SalleRepository();

    @FXML
    void btnAjouter(ActionEvent event) {
        String libelle = champLibelle.getText().trim();

        if (libelle.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Le champ libellé est vide.");
            return;
        }

        try {
            Salle salle = new Salle();
            salle.setLibelle(libelle);
            salleRepository.addSalle(salle);

            showAlert(Alert.AlertType.INFORMATION, "Validation", "Enregistrement réussi");
            champLibelle.clear();
            afficherSalle();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Enregistrement échoué");
        }
    }

    @FXML
    void btnAnnuler(ActionEvent event) {
        champLibelle.clear();
        table.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSupprimer(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();

        if (!hasAccess(loggedInUser)) return;

        Salle selectedSalle = table.getSelectionModel().getSelectedItem();
        if (selectedSalle == null) {
            showAlert(Alert.AlertType.WARNING, "Suppression", "Veuillez sélectionner une salle.");
            return;
        }

        try {
            salleRepository.deleteSalle(selectedSalle.getId());
            afficherSalle();
            champLibelle.clear();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression échouée");
        }
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();

        if (!hasAccess(loggedInUser)) return;

        Salle selectedSalle = table.getSelectionModel().getSelectedItem();
        if (selectedSalle == null) {
            showAlert(Alert.AlertType.WARNING, "Mise à jour", "Veuillez sélectionner une salle.");
            return;
        }

        String libelle = champLibelle.getText().trim();
        if (libelle.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Le champ libellé est vide.");
            return;
        }

        try {
            Salle salle = new Salle();
            salle.setId(selectedSalle.getId());
            salle.setLibelle(libelle);
            salleRepository.updateSalle(salle);

            showAlert(Alert.AlertType.INFORMATION, "Mise à jour", "Salle mise à jour avec succès");
            champLibelle.clear();
            afficherSalle();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Mise à jour échouée");
        }
    }

    @FXML
    void charge(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Salle salle = table.getSelectionModel().getSelectedItem();
            if (salle != null) {
                champLibelle.setText(salle.getLibelle());
            }
        }
    }

    public void afficherSalle() {
        try {
            List<Salle> salles = salleRepository.getAllSalle();
            ObservableList<Salle> res = FXCollections.observableArrayList(salles);
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            table.setItems(res);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher les salles");
        }
    }

    private boolean hasAccess(Utilisateur user) {
        if (user == null || (!user.getRole().equals("admin") && !user.getRole().equals("gestionnaire"))) {
            showAlert(Alert.AlertType.ERROR, "Accès refusé", "Vous n'avez pas les droits nécessaires pour effectuer cette action");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherSalle();
    }
}
