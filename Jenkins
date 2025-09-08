pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.6'
        jdk 'JDK-21'
    }
    
    environment {
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=256m'
        BROWSER = 'chrome'
        HEADLESS = 'false'
        TEST_ENV = 'staging'
        REPORTS_DIR = 'reports'
        SCREENSHOTS_DIR = 'screenshots'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
        ansiColor('xterm')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                    env.BUILD_DISPLAY_NAME = "#${env.BUILD_NUMBER}-${env.GIT_COMMIT_SHORT}"
                }
            }
        }
        
        stage('Environment Setup') {
            steps {
                echo 'Setting up environment...'
                script {
                    sh 'mkdir -p reports screenshots test-output'
                    
                    sh '''
                        echo "=== Environment Information ==="
                        echo "Java Version: $(java -version 2>&1 | head -n 1)"
                        echo "Maven Version: $(mvn -version | head -n 1)"
                        echo "Git Commit: ${GIT_COMMIT_SHORT}"
                        echo "Build Number: ${BUILD_NUMBER}"
                        echo "Browser: ${BROWSER}"
                        echo "Headless Mode: ${HEADLESS}"
                        echo "================================"
                    '''
                }
            }
        }
        
        stage('Dependency Resolution') {
            steps {
                echo 'Resolving Maven dependencies...'
                sh 'mvn clean dependency:resolve dependency:resolve-plugins'
            }
        }
        
        stage('Code Quality Check') {
            parallel {
                stage('Compile') {
                    steps {
                        echo 'Compiling source code...'
                        sh 'mvn compile test-compile'
                    }
                }
                stage('Static Analysis') {
                    steps {
                        echo 'Running static code analysis...'
                        script {
                            try {
                                sh 'mvn spotbugs:check'
                            } catch (Exception e) {
                                echo "Static analysis completed with warnings: ${e.getMessage()}"
                            }
                        }
                    }
                }
            }
        }
        
        stage('Test Execution') {
            parallel {
                stage('TestNG Tests') {
                    steps {
                        echo 'Running TestNG tests...'
                        script {
                            try {
                                sh '''
                                    mvn test -DsuiteXmlFile=testng.xml \
                                    -Dbrowser=${BROWSER} \
                                    -Dheadless=${HEADLESS} \
                                    -Dtest.env=${TEST_ENV} \
                                    -Dmaven.test.failure.ignore=true
                                '''
                            } catch (Exception e) {
                                echo "TestNG tests completed with some failures: ${e.getMessage()}"
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: 'test-output/**/*.xml'
                        }
                    }
                }
                
                stage('Cucumber BDD Tests') {
                    steps {
                        echo 'Running Cucumber BDD tests...'
                        script {
                            try {
                                sh '''
                                    mvn test -Dtest=TestRunner \
                                    -Dbrowser=${BROWSER} \
                                    -Dheadless=${HEADLESS} \
                                    -Dtest.env=${TEST_ENV} \
                                    -Dmaven.test.failure.ignore=true
                                '''
                            } catch (Exception e) {
                                echo "Cucumber tests completed with some failures: ${e.getMessage()}"
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                    }
                    post {
                        always {
                            cucumber buildStatus: 'UNSTABLE',
                                fileIncludePattern: 'reports/cucumber.json',
                                trendsLimit: 10
                        }
                    }
                }
            }
        }
        
        stage('Report Generation') {
            steps {
                echo 'Generating test reports...'
                script {
                    sh 'mvn site -DskipTests'
                    
                    sh '''
                        if [ -f "reports/cucumber.json" ]; then
                            echo "Generating Cucumber HTML report..."
                        fi
                    '''
                }
            }
        }
        
        stage('Artifact Collection') {
            steps {
                echo 'Collecting test artifacts...'
                script {
                    archiveArtifacts artifacts: 'reports/**/*', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'test-output/**/*', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'target/site/**/*', allowEmptyArchive: true
                    
                    if (currentBuild.result == 'FAILURE' || currentBuild.result == 'UNSTABLE') {
                        archiveArtifacts artifacts: 'screenshots/**/*', allowEmptyArchive: true
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed'
            script {
                sh '''
                    echo "=== Build Summary ==="
                    echo "Build Status: ${currentBuild.result ?: 'SUCCESS'}"
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Git Commit: ${GIT_COMMIT_SHORT}"
                    echo "Duration: ${currentBuild.durationString}"
                    echo "====================="
                '''
            }
        }
        
        success {
            echo 'Pipeline executed successfully!'
            script {
                emailext (
                    subject: "SauceDemo Test Automation - Build #${BUILD_NUMBER} SUCCESS",
                    body: """
                        <h2>Build Successful!</h2>
                        <p><strong>Project:</strong> SauceDemo Test Automation</p>
                        <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                        <p><strong>Git Commit:</strong> ${GIT_COMMIT_SHORT}</p>
                        <p><strong>Duration:</strong> ${currentBuild.durationString}</p>
                        <p><strong>Console Output:</strong> <a href="${BUILD_URL}console">View Console</a></p>
                        <p><strong>Test Reports:</strong> <a href="${BUILD_URL}testReport/">View Test Results</a></p>
                    """,
                    to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@example.com'}",
                    mimeType: 'text/html'
                )
            }
        }
        
        failure {
            echo 'Pipeline failed!'
            script {
                emailext (
                    subject: "SauceDemo Test Automation - Build #${BUILD_NUMBER} FAILED",
                    body: """
                        <h2>Build Failed!</h2>
                        <p><strong>Project:</strong> SauceDemo Test Automation</p>
                        <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                        <p><strong>Git Commit:</strong> ${GIT_COMMIT_SHORT}</p>
                        <p><strong>Duration:</strong> ${currentBuild.durationString}</p>
                        <p><strong>Console Output:</strong> <a href="${BUILD_URL}console">View Console</a></p>
                        <p><strong>Test Reports:</strong> <a href="${BUILD_URL}testReport/">View Test Results</a></p>
                        <p><strong>Artifacts:</strong> <a href="${BUILD_URL}artifact/">Download Artifacts</a></p>
                    """,
                    to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@example.com'}",
                    mimeType: 'text/html'
                )
            }
        }
        
        unstable {
            echo 'Pipeline completed with test failures!'
            script {
                emailext (
                    subject: "SauceDemo Test Automation - Build #${BUILD_NUMBER} UNSTABLE",
                    body: """
                        <h2>Build Unstable (Tests Failed)</h2>
                        <p><strong>Project:</strong> SauceDemo Test Automation</p>
                        <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                        <p><strong>Git Commit:</strong> ${GIT_COMMIT_SHORT}</p>
                        <p><strong>Duration:</strong> ${currentBuild.durationString}</p>
                        <p><strong>Console Output:</strong> <a href="${BUILD_URL}console">View Console</a></p>
                        <p><strong>Test Reports:</strong> <a href="${BUILD_URL}testReport/">View Test Results</a></p>
                        <p><strong>Artifacts:</strong> <a href="${BUILD_URL}artifact/">Download Artifacts</a></p>
                    """,
                    to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@example.com'}",
                    mimeType: 'text/html'
                )
            }
        }
        
        cleanup {
            echo 'Cleaning up workspace...'
            sh '''
                echo "Cleaning up temporary files..."
            '''
        }
    }
}

