package org.example.gestionpresencesprofesseurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.example.gestionpresencesprofesseurs.repository.EmargementRepository;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private LineChart<Number, Number> linechart;
    @FXML
    private PieChart piechart;
    private final EmargementRepository emargementRepository = new EmargementRepository();
    public void afficherGraphiqueEmargements() {
        barchart.getData().clear(); // Effacer les anciennes données

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Présences par Professeur");

        Map<String, Long> data = emargementRepository.getEmargementsParProfesseur();
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barchart.getData().add(series);
    }
    public void afficherGraphiqueTauxPresence() {
        piechart.getData().clear(); // Effacer les anciennes données

        Map<String, Double> data = emargementRepository.getTauxPresenceParCours();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + " (" + entry.getValue() + "%)", entry.getValue()));
        }

        piechart.setData(pieChartData);

        // Styliser pour un effet de doughnut
        piechart.setLabelsVisible(true);
        piechart.setStartAngle(90);
    }

//    public void afficherGraphiqueEmargementsParInterval(String interval) {
//        linechart.getData().clear(); // Effacer les anciennes données
//
//        Map<LocalDate, Long> data = emargementRepository.getEmargementsParDate(interval);
//        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        series.setName("Évolution des Présences");
//
//        for (Map.Entry<LocalDate, Long> entry : data.entrySet()) {
//            series.getData().add(new XYChart.Data<>(entry.getKey().toEpochDay(), entry.getValue()));
//        }
//
//        linechart.getData().add(series);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        intervalComboBox.getItems().addAll("jour", "semaine", "mois");
//        intervalComboBox.getSelectionModel().selectFirst();
//        intervalComboBox.setOnAction(event -> {
//            String interval = intervalComboBox.getValue();
//            afficherGraphiqueEmargementsParInterval(interval);
//        });
        afficherGraphiqueEmargements();
        afficherGraphiqueTauxPresence();
        //afficherGraphiqueEmargementsParInterval("mois");
    }
}
