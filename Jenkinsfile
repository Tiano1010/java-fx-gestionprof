pipeline {
    agent any

    environment {
        IMAGE_NAME = "ka_habib_emargement"
    }

    stages {
        stage('Pull du code') {
            steps {
                git branch: 'Ka_Habib_emargement',
                    url: 'https://github.com/Tiano1010/https://github.com/Tiano1010/java-fx-gestionprof.git'
            }
        }

        stage('Installer les dépendances Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Créer une image Docker') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}")
                }
            }
        }
    }

    triggers {
        githubPush()
    }
}
