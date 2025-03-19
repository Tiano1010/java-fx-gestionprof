module org.example.gestionpresencesprofesseurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core; // Ajout de Hibernate
    requires java.sql;
    requires static lombok;
    requires java.mail; // Requis pour les connexions JDBC avec Hibernate

    opens org.example.gestionpresencesprofesseurs to javafx.fxml;
    opens org.example.gestionpresencesprofesseurs.model to org.hibernate.orm.core; // Ouvrir les entités Hibernate

    exports org.example.gestionpresencesprofesseurs;
    exports org.example.gestionpresencesprofesseurs.model;
    exports org.example.gestionpresencesprofesseurs.repository;
}
