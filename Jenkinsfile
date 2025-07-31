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
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    bat "mvn clean test -P${params.profile} -Dsurefire.failIfNoSpecifiedTests=false"
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat 'allure generate target/allure-results -o allure-report --clean'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            emailext(
                subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Allure Report",
                body: """Hello,<br><br>
                         Please view the Allure Test Report for build # ${env.BUILD_NUMBER} at the link below:<br>
                         <a href="${env.BUILD_URL}allure">Click here to view report</a><br><br>
                         Regards,<br>
                         Jenkins""",
                mimeType: 'text/html',
                to: "khanali6861@gmail.com"
            )
        }
    }
}
