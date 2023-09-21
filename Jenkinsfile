pipeline {
    agent any
    stages {
        stage('Build Application') {
            steps{
                sh 'mvn clean package'
            }
            post {
                success {
                    echo "Now Archiving the Artifacts..."
                    sh 'mv target/*.war target/springwebflux.war'
                    archiveArtifacts artifacts: 'target/*.war'
                }
            }
        }
        stage('Deploy Application to Staging Server') {
            steps{
                build job: 'Deploy_Application_Staging_Server'
            }
        }
    }
}