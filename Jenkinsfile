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

        stage('Generate Allure Report') {
            steps {
                // Generate Allure report from results
                bat 'allure generate target/allure-results -o allure-report --clean'
            }
        }

        stage('Zip Allure Report') {
            steps {
                // Zip the Allure report
                bat 'powershell Compress-Archive -Path "allure-report\\*" -DestinationPath "allure-report.zip" -Force'
            }
        }
    }

    post {
        always {
            // Email with ZIP attachment
            emailext(
                subject: "$PROJECT_NAME - Build # $BUILD_NUMBER - Allure Report",
                body: """Hello,<br><br>
                         Please find attached the Allure Test Report for build # $BUILD_NUMBER.<br><br>
                         Regards,<br>
                         Jenkins""",
                mimeType: 'text/html',
                attachmentsPattern: "allure-report.zip",
                to: "khanali6861@gmail.com"
            )
        }
    }
}
