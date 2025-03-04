package org.example.gestionpresencesprofesseurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane dynamiquePage;

    @FXML
    void pageUtilisateur(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("register-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }


    @FXML
    void pageSalle(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin") || loggedInUser.getRole().equals("gestionnaire")  ) {
            Parent fxml = FXMLLoader.load(getClass().getResource("classe-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }



    @FXML
    void pageCours(ActionEvent event) throws IOException {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin") || loggedInUser.getRole().equals("gestionnaire")  ) {
            Parent fxml = FXMLLoader.load(getClass().getResource("cours-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }

    @FXML
    void pageProffesseur(ActionEvent event) throws IOException{

    }

    @FXML
    void btnDeconnect(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Voulez-vous vraiment quitter l'application?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
        {
            Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxml);
            Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}