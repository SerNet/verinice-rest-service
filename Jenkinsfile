pipeline {
    agent {
        docker {
            image 'maven:3-jdk-11'
        }
    }
    environment {
        JAVA_HOME = '/usr/local/openjdk-11'
        MAVEN_OPTS = "-DproxySet=true -DproxyHost=cache.sernet.private -DproxyPort=3128"
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
                sh 'mvn clean install'
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
            junit allowEmptyResults: true, testResults: '**/target/test-results/**/*.xml'
        }
        success {
            archiveArtifacts artifacts: 'verinice-rest/target/verinice-rest*.jar', fingerprint: true
        }
    }
}
