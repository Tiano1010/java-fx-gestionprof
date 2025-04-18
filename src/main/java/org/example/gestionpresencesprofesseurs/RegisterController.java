package org.example.gestionpresencesprofesseurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.UtilisateurRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField champEmail;

    @FXML
    private PasswordField champMdp;

    @FXML
    private TextField champNom;

    @FXML
    private TextField champPrenom;

    @FXML
    private TextField champTelephone;

    @FXML
    private ComboBox<?> combo;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colPrenom;

    @FXML
    private TableColumn<?, ?> colProfile;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<Utilisateur> table;

    //    @FXML
    //    void btnAnnuler(ActionEvent event) throws IOException {
    //        Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
    //        Scene scene = new Scene(fxml);
    //        Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    //        stage.setTitle("Connexion");
    //        stage.setScene(scene);
    //        stage.show();
    //    }

    @FXML
    void btnValider(ActionEvent event) {
        String nom = champNom.getText();
        String prenom = champPrenom.getText();
        String email = champEmail.getText();
        String motDePasse = champMdp.getText();
        String role = combo.getValue().toString();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setMotDePasse(motDePasse);
            utilisateur.setRole(role);
            utilisateurRepository.addUtilisateur(utilisateur);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Enreistrement reussi");
            alert.showAndWait();

            //            Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
            //            Scene scene = new Scene(fxml);
            //            Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            //            stage.setTitle("Connexion");
            //            stage.setScene(scene);
            //            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Enregistrement echoué");
            alert.showAndWait();
        } finally {
            entityManager.close();
        }
        champEmail.setText("");
        champMdp.setText("");
        champNom.setText("");
        champPrenom.setText("");
    }

    // Envoyer un email après avoir enregistré un cours
    @FXML
    void btnDelete(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin")) {
            Long id = table.getSelectionModel().getSelectedItem().getId();
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            try {
                utilisateurRepository.deleteUtilisateur(id);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            afficherUtilisateur();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour effectuer cette action");
            alert.showAndWait();
        }
    }

    @FXML
    void btnAnnuler(ActionEvent event) {
        champNom.clear();
        champPrenom.clear();
        champEmail.clear();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin")) {
            Long id = table.getSelectionModel().getSelectedItem().getId();
            String nom = champNom.getText();
            String prenom = champPrenom.getText();
            String email = champEmail.getText();
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            try {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(id);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setEmail(email);
                utilisateurRepository.updateUtilisateur(utilisateur);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            btnAnnuler(event);
            afficherUtilisateur();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour effectuer cette action");
            alert.showAndWait();
        }
    }

    @FXML
    void charge(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Utilisateur utilisateur = table.getSelectionModel().getSelectedItem();
            champNom.setText(utilisateur.getNom());
            champPrenom.setText(utilisateur.getPrenom());
            champEmail.setText(utilisateur.getEmail());
        }
    }

    public void afficherUtilisateur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        try {
            List<Utilisateur> utilisateurs = utilisateurRepository.getAllUtilisateur();
            ObservableList<Utilisateur> res = FXCollections.observableArrayList(utilisateurs);
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colProfile.setCellValueFactory(new PropertyValueFactory<>("role"));
            table.setItems(res);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherUtilisateur();
        ObservableList items = combo.getItems();
        items.add("professeur");
        items.add("gestionnaire");
        //items.add("admin");
    }
}
