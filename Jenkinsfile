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
                // Even if tests fail, mark as UNSTABLE instead of aborting pipeline
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    bat "mvn clean test -P${params.profile} -Dsurefire.failIfNoSpecifiedTests=false"
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                // Always run report generation
                bat 'allure generate target/allure-results -o allure-report --clean'
            }
        }

        stage('Zip Allure Report') {
            steps {
                bat 'powershell Compress-Archive -Path "allure-report\\*" -DestinationPath "allure-report.zip" -Force'
            }
        }
    }

    post {
        always {
            emailext(
                subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Allure Report",
                body: """Hello,<br><br>
                         Please find attached the Allure Test Report for build # ${env.BUILD_NUMBER}.<br><br>
                         Regards,<br>
                         Jenkins""",
                mimeType: 'text/html',
                attachmentsPattern: "allure-report.zip",
                to: "khanali6861@gmail.com"
            )
        }
    }
}
