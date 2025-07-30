pipeline {
    agent any

    parameters {
        choice(name: 'profile', choices: ['smoke', 'regression', 'sanity'], description: 'Select Test Suite to Run')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/alikhan447860/SeleniumAutomation.git'
            }
        }

        stage('Build & Test') {
            steps {
               bat "mvn clean test -P${params.profile} -Dsurefire.failIfNoSpecifiedTests=false"
            }
        }
    }

    post {
       always {
           script {
               allure([
                   results: [[path: 'target/allure-results']],
                   commandline: 'allure'
               ])
           }
       }
    }
}
