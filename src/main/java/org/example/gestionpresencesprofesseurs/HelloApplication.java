package org.example.gestionpresencesprofesseurs;

import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false); // ✅ Empêche le redimensionnement
        stage.centerOnScreen();    // ✅ Centre la fenêtre
        stage.show();

        // Initialisation de l'EntityManagerFactory
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    public static void main(String[] args) {
        launch();
    }
}
