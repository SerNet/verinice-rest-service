pipeline {
    agent {
        dockerfile {
            args '-v $MAVEN_REPOSITORY_BASE/repository$EXECUTOR_NUMBER:/m2/repository'
        }
    }
    environment {
        JAVA_HOME = '/usr/local/openjdk-11'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    stages {
        stage('Setup') {
            steps {
                sh 'env'
                buildDescription "${env.GIT_BRANCH} ${env.GIT_COMMIT[0..8]}"
            }
        }
        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
    post {
        always {
            recordIssues(tools: [java()])
            recordIssues(tools: [javaDoc()])
            recordIssues(tools: [taskScanner(highTags: 'FIXME', ignoreCase: true, normalTags: 'TODO', includePattern: '**/*.java, **/.xml')])
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
        success {
            archiveArtifacts artifacts: 'verinice-rest/target/verinice-rest*.jar', fingerprint: true
        }
    }
}
