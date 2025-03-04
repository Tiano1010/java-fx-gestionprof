package org.example.gestionpresencesprofesseurs;

import org.example.gestionpresencesprofesseurs.model.Utilisateur;

public class UserSession {
        private static UserSession instance;
        private Utilisateur loggedInUser;

        private UserSession() {}

        public static UserSession getInstance() {
            if (instance == null) {
                instance = new UserSession();
            }
            return instance;
        }


        public Utilisateur getLoggedInUser() {
            return loggedInUser;
        }

        public void setLoggedInUser(Utilisateur user) {
            this.loggedInUser = user;
        }

        public void cleanUserSession() {
            loggedInUser = null;
        }
    }

