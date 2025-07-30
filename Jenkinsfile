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
                bat "mvn clean test"
"
            }
        }

        stage('Post-build') {
            steps {
                echo "Build and Tests completed!"
            }
        }
    }
}
