pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/alikhan447860/SeleniumAutomation.git'
            }
        }

        stage('Build & Test') {
            steps {
               bat "mvn clean test -Dsurefire.failIfNoSpecifiedTests=false"
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
