#!/usr/bin/env groovy
@Library('peon-pipeline') _

node {
    def APP_NAME    = "tpregisteret"
    def APP_TOKEN   = github.generateAppToken()
    def DOCKER_REPO = "repo.adeo.no:5443"
    def COMMIT_HASH_LONG
    def COMMIT_HASH_SHORT
    stage('checkout') {
        try {
            sh "git init"
            sh "git pull https://x-access-token:${APP_TOKEN}@github.com/navikt/${APP_NAME}.git"
            COMMIT_HASH_LONG = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
            COMMIT_HASH_SHORT = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
            github.commitStatus("pending", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
        } catch (err) {
            github.commitStatus("failure", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'danger',
                       message: "Failed to checkout ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>"
            ])
            error("Failed checkout stage")
        }
    }
    stage('build') {
        try {
            sh '''docker run --rm -t \
                    -w /usr/src \
                    -v ${PWD}:/usr/src \
                    -v ${HOME}/.m2:/var/maven/.m2 \
                    -e MAVEN_CONFIG=/var/maven/.m2 \
                    maven:3.5-jdk-11 mvn -Duser.home=/var/maven clean package -DskipTests=true -B -V
                '''
            sh '''docker run --rm -t \
                    -w /usr/src \
                    -v ${PWD}:/usr/src \
                    -v /var/run/docker.sock:/var/run/docker.sock \
                    -v ${HOME}/.m2:/var/maven/.m2 \
                    -e MAVEN_CONFIG=/var/maven/.m2 \
                    maven:3.5-jdk-11 mvn -Duser.home=/var/maven verify -B -e
                '''
            github.createDeploymentStatus(APP_TOKEN, "navikt/${APP_NAME}", COMMIT_HASH_SHORT, "success")
        } catch (err) {
            github.commitStatus("failure", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'danger',
                       message: "Failed to build ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>"
            ])
            error("Failed build stage")
        }
    }
    stage('release') {
        try {
            withCredentials([usernamePassword(credentialsId: 'nexusUploader',
                                usernameVariable: 'NEXUS_USERNAME',
                                passwordVariable: 'NEXUS_PASSWORD'
                            )]) {
                sh "docker login -u ${env.NEXUS_USERNAME} -p ${env.NEXUS_PASSWORD} ${DOCKER_REPO}"
                sh "docker build . --pull -t ${DOCKER_REPO}/${APP_NAME}:${COMMIT_HASH_SHORT}"
                sh "docker push ${DOCKER_REPO}/${APP_NAME}:${COMMIT_HASH_SHORT}"
            }
        } catch (err) {
            github.commitStatus("failure", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'danger',
                       message: "Failed to upload ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`> to nexus"
            ])
            error("Failed release stage")
        }
    }
    stage('deploy dev') {
        try {
            sh "sed -i \'s/{{latest}}/${COMMIT_HASH_SHORT}/\' nais.yaml"
            sh "sed -i \'s/{{context}}/nais.preprod.local/\' nais.yaml"
            sh "kubectl config use-context dev-fss"
            sh "kubectl apply -f nais.yaml"
            sh "kubectl rollout status -w deployment/${APP_NAME}"
            sh "sed -i \'s/${COMMIT_HASH_SHORT}/{{latest}}/\' nais.yaml"
            sh "sed -i \'s/nais.preprod.local/{{context}}/\' nais.yaml"
            github.commitStatus("success", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'good',
                       message: "Successfully deployed ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>" + " to dev-fss :frog:"
            ])
        } catch (err) {
            github.commitStatus("failure", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'danger',
                       message: "Failed to deploy ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>" + " to dev-fss :gasp:"
            ])
            error("Failed deploy to dev stage")
        }
    }
    stage('deploy prod') {
        try {
            sh "sed -i \'s/{{latest}}/${COMMIT_HASH_SHORT}/\' nais.yaml"
            sh "sed -i \'s/{{context}}/nais.adeo.no/\' nais.yaml"
            sh "kubectl config use-context prod-fss"
            sh "kubectl apply -f nais.yaml"
            sh "kubectl rollout status -w deployment/${APP_NAME}"
            sh "sed -i \'s/${COMMIT_HASH_SHORT}/{{latest}}/\' nais.yaml"
            sh "sed -i \'s/nais.adeo.no/{{context}}/\' nais.yaml"
            github.commitStatus("success", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'good',
                       message: "Successfully deployed ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>" + " to prod-fss :frog:"
            ])
        } catch (err) {
            github.commitStatus("failure", "navikt/${APP_NAME}", APP_TOKEN, COMMIT_HASH_LONG)
            slackSend([color  : 'danger',
                       message: "Failed to deploy ${APP_NAME}:<https://github.com/navikt/${APP_NAME}/commit/${COMMIT_HASH_LONG}|`${COMMIT_HASH_SHORT}`>" + " to prod-fss :gasp:"
            ])
            error("Failed deploy to prod stage")
        }
    }
}
