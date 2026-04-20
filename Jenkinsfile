pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout Code') {
    steps {
        git branch: 'feature_rachit13042026', 
            url: 'https://github.com/RS125772/selenium-java-capstone.git'
    }
}

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dbrowser=chrome-headless'
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'reports/**', fingerprint: true
            }
        }
    }
}