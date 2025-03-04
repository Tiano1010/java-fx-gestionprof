package org.example.gestionpresencesprofesseurs;

import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.UtilisateurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField champLogin;
    @FXML
    private PasswordField champMdp;
    private String login;
    private String password;

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        login = champLogin.getText();
        password = champMdp.getText();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        if (login.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else {
            Utilisateur utilisateur = utilisateurRepository.getconn(login, password);
            if(utilisateur != null){
                //garder la session
                UserSession.getInstance().setLoggedInUser(utilisateur);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connexion réussie");
                alert.setHeaderText(null);
                alert.setContentText("Vous êtes maintenant connecté");
                alert.showAndWait();

                Parent fxml = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Scene scene = new Scene(fxml);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Accueil");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Login ou mot de passe incorrect");
                alert.showAndWait();
            }
            entityManager.close();
            champLogin.setText("");
            champMdp.setText("");
        }
    }

    @FXML
    void btnRegister(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Scene scene = new Scene(fxml);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }
}

