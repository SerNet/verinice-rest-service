def withDockerNetwork(Closure inner) {
    def networkId = UUID.randomUUID().toString()
    try {
        sh "docker network create ${networkId}"
        inner.call(networkId)
    } finally {
        sh "docker network rm ${networkId}"
    }
}


pipeline {
    agent none
    environment {
        JAVA_HOME = '/usr/local/openjdk-11'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    stages {
        stage('Setup') {
            agent any
            steps {
                sh 'env'
                buildDescription "${env.GIT_BRANCH} ${env.GIT_COMMIT[0..8]}"
            }
        }
        stage('Build') {
            agent {
                dockerfile {
                    args '-v $MAVEN_REPOSITORY_BASE/repository$EXECUTOR_NUMBER:/m2/repository'
                }
            }
            steps {
                sh './mvnw clean package'
                archiveArtifacts artifacts: 'verinice-rest/target/verinice-rest*.jar', fingerprint: true
            }
            post {
                always {
                    recordIssues(tools: [java()])
                    recordIssues(tools: [javaDoc()])
                    recordIssues(tools: [taskScanner(highTags: 'FIXME', ignoreCase: true, normalTags: 'TODO', includePattern: '**/*.java, **/.xml')])
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Run integration tests') {
            agent any
            steps {
                script {
                    withDockerNetwork{ n ->
                        docker.image("postgres:13.4-alpine").withRun("--network ${n} --name database-${n} -e POSTGRES_USER=verinice -e POSTGRES_PASSWORD=verinice -e POSTGRES_DB=verinicedb") { db ->
                            docker.build('verinice-rest-service-build').inside("--network ${n} -v $MAVEN_REPOSITORY_BASE/repository$EXECUTOR_NUMBER:/m2/repository"){
                                timeout(5) {
                                    waitUntil {
                                        def r = sh returnStatus:true, script: "pg_isready -h database-${n} -U verinice -d verinicedb"
                                        return (r == 0)
                                    }
                                }
                                sh "PGPASSWORD=verinice psql -h database-${n} -q -U verinice -d verinicedb -f verinice-rest/src/test/resources/test-dump.sql"
                                sh "SKIPINIT=1 VERINICEDBSERVER=database-${n}:5432 VERINICEDB=verinicedb ./integration-test"
                                junit testResults: 'verinice-rest/src/test/python/results.xml'
                            }
                        }
                    }
                }
            }
        }
    }
}
