package org.example.gestionpresencesprofesseurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.example.gestionpresencesprofesseurs.model.Salle;
import org.example.gestionpresencesprofesseurs.repository.SalleRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoursController implements Initializable {

    @FXML
    private ComboBox<Salle> combo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SalleRepository salleRepository = new SalleRepository();
        List<Salle> salleList = salleRepository.getAllSalle();
        ObservableList<Salle> res = FXCollections.observableArrayList(salleList);
        combo.setItems(res);
    }
}
