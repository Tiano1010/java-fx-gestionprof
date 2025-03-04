package org.example.gestionpresencesprofesseurs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.model.Utilisateur;
import org.example.gestionpresencesprofesseurs.repository.SalleRepository;
import org.example.gestionpresencesprofesseurs.repository.UtilisateurRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalleController implements Initializable {

    @FXML
    private TextField champLibelle;

    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableView<Salle> table;
    @FXML
    private TableColumn<?, ?> colLibelle;
    @FXML
    void btnAjouter(ActionEvent event) {
        String libelle = champLibelle.getText();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
         SalleRepository salleRepository = new SalleRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Salle salle = new Salle();
            salle.setLibelle(libelle);
            salleRepository.addSalle(salle);
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
        btnAnnuler(event);
        afficherSalle();
    }


    @FXML
    void btnAnnuler(ActionEvent event) {
       champLibelle.clear();
    }

    @FXML
    void btnSupprimer(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser.getRole().equals("admin") || loggedInUser.getRole().equals("gestionnaire")) {
            Long id = table.getSelectionModel().getSelectedItem().getId();
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            SalleRepository salleRepository= new SalleRepository();
            try {
                salleRepository.deleteSalle(id);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour effectuer cette action");
            alert.showAndWait();
        }
        afficherSalle();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser.getRole().equals("admin") || loggedInUser.getRole().equals("gestionnaire")) {
            Long id = table.getSelectionModel().getSelectedItem().getId();
            String libelle = champLibelle.getText();
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            SalleRepository salleRepository = new SalleRepository();
            try {
                Salle salle = new Salle();
                salle.setId(id);
                salle.setLibelle(libelle);
                salleRepository.updateSalle(salle);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            btnAnnuler(event);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour effectuer cette action");
            alert.showAndWait();
        }
        afficherSalle();
        btnAnnuler(event);
    }
    public void afficherSalle() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SalleRepository sallerepository = new SalleRepository();
        try {
            List<Salle> salles = sallerepository.getAllSalle();
            ObservableList<Salle> res = FXCollections.observableArrayList(salles);
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            table.setItems(res);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @FXML
    void charge(MouseEvent event) {
        if(event.getClickCount() == 2){
            Salle salle= (Salle) table.getSelectionModel().getSelectedItem();
            champLibelle.setText(salle.getLibelle());
        };
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       afficherSalle();
    }
}












































