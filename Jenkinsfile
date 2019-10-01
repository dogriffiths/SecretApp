#!groovy

slack 'Starting build pipeline for Secret App '

properties properties: [
        disableConcurrentBuilds()
]

pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('SCM Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Clean') {
            steps {
                script {
                    sh 'make clean'
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    ansiColor('xterm') {
                        sh 'make build'
                    }
                    slack 'App built'
                }
            }
        }

        stage('Test') {
            steps {
                lock(resource: 'timek-dev') {
                    ansiColor('xterm') {
                        sh 'make uninstall || echo Uninstall'
                        sh 'make start-record'
                        sh 'make test'
                    }
                    slack 'App tested'
                }
            }
        }

        stage('Publish App') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'master') {
                        ansiColor('xterm') {
                            sh 'make build-app-release'
                        }
                        gitCommit = releaseNotes(BUILD_NUMBER)
//                        androidApkUpload apkFilesPattern: '**/app-release.apk', googleCredentialsId: 'secretapp-publish', recentChangeList: [[language: 'en-US', text: "${gitCommit}"]], trackName: 'alpha'
                        slack "App published\n${gitCommit}"
                        sh "(git tag -d published-${BUILD_NUMBER} || echo 'No local tag to delete'); git tag -a published-${BUILD_NUMBER} -m 'Version published-${BUILD_NUMBER}'; (git push --delete origin published-${BUILD_NUMBER} || echo 'No remote tag'); git push --tags"
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                sh "kill `cat /tmp/recordpid` || echo 'No need to kill old process'"
                sh "rm -rf /Volumes/media/secretapp-webtest/*"
                sh "rm -f features/*"
                sh "make stop-record gather-features"
//                junit 'app/build/**/TEST-*.xml'
//                junit 'app/build/spoon/**/junit-reports/*.xml'
//                archiveArtifacts allowEmptyArchive: true, artifacts: 'app/build/spoon/debug/**'
//                archiveArtifacts allowEmptyArchive: true, artifacts: 'app/build/reports/**'
//                archiveArtifacts allowEmptyArchive: true, artifacts: 'app/build_*/reports/**'
//                cucumber 'features/*cucumber*.json'
//                sh "make publish-tests"
//                sh "cp -rf ../builds/\$(ls -t ../builds/|head -1)/cucumber-html-reports /Volumes/media/secretapp-webtest/||echo 'Cannot find it'"
                slack "See webtest results at ready"
            }
//            step([$class: 'CukedoctorPublisher', featuresDir: 'archive/features', format: 'HTML', hideFeaturesSection: false, hideScenarioKeyword: false, hideStepTime: false, hideSummary: false, hideTags: false, numbered: true, sectAnchors: true, title: 'Living Documentation', toc: 'RIGHT'])
        }
        failure {
            slack "Build failed: ${currentBuild.result}", '#ff0000'
        }
    }
}

def slack(String msg, String color = '#000000') {
    slackSend channel: 'builds', color: color, message: msg, teamDomain: 'aspenshore', tokenCredentialId: 'timekeeper-slack'
}

def releaseNotes(String buildNumber) {
    gitCommit = sh(returnStdout: true, script: "bin/release_message.sh ${buildNumber}").trim()
    if (gitCommit.size() > 500) {
        gitCommit = gitCommit.substring(0, 497) + '...'
    }
    return gitCommit
}
