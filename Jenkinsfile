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
                sh './mvnw clean install'
            }
        }
    }
    post {
        always {
            recordIssues(tools: [spotBugs(pattern: '**/target/reports/spotbugs/main.xml', useRankAsPriority: true)])
            recordIssues(tools: [pmdParser(pattern: '**/target/reports/pmd/main.xml')])
            recordIssues(tools: [java()])
            recordIssues(tools: [javaDoc()])
            recordIssues(tools: [taskScanner(highTags: 'FIXME', ignoreCase: true, normalTags: 'TODO', includePattern: '**/*.java, **/.xml')])
            jacoco classPattern: '**/target/classes/java/main'
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
        success {
            archiveArtifacts artifacts: 'verinice-rest/target/verinice-rest*.jar', fingerprint: true
        }
    }
}
