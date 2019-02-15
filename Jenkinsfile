pipeline {
    agent any

    environtment {
        APP_NAME    = 'tpregisteret'
        DOCKER_REPO = 'repo.adeo.no:5443'
        COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
    }

    stages {
        stage('checkout') {
            steps {
                sh 'git init'
                sh 'git pull https://github.com/navikt/$APP_NAME.git'
            }
            post {
                github.commitStatus("pending", 'navikt/$APP_NAME', $APP_TOKEN, $COMMIT_HASH)
            }
        }

        stage('build') {
            steps {
                docker run --rm -t \
                    -v ${PWD}:/usr/src \
                    -w /usr/src -u $(shell id -u) \
                    -v ${HOME}/.m2:/var/maven/.m2 \
                    -e MAVEN_CONFIG=/var/maven/.m2 \
                    maven:3.5-jdk-11 mvn -Duser.home=/var/maven clean package -DskipTests=true -B -V

                docker run --rm -t \
                    -v ${PWD}:/usr/src \
                    -v /var/run/docker.sock:/var/run/docker.sock \
                	-w /usr/src -u $(shell id -u) \
                	-v ${HOME}/.m2:/var/maven/.m2 \
                	-e MAVEN_CONFIG=/var/maven/.m2 \
                	maven:3.5-jdk-11 mvn -Duser.home=/var/maven verify -B -e
            }
        }

        stage('release') {
            steps {
                withCredentials([usernamePassword(
                                    credentialsId: 'nexusUploader',
                                    usernameVariable: 'NEXUS_USERNAME',
                                    passwordVariable: 'NEXUS_PASSWORD'
                                )]) {
                    sh 'docker login -u ${env.NEXUS_USERNAME} -p ${env.NEXUS_PASSWORD} $DOCKER_REPO'
                    sh 'docker push $/tpregisteret:$COMMIT_HASH''
                }
            }
        }

        stage('deploy') {
            steps {
                sh 'script: sed 's/latest/$COMMIT_HASH/' nais.yaml | tee nais.yaml'

                kubectl config use-context dev-fss
                kubectl apply -f nais.yaml --wait
                kubectl rollout status -w deployment/${APP_NAME}
            }
        }

        post {
            success {
                github.commitStatus("success", 'navikt/${APP_NAME}', $APP_TOKEN, $COMMIT_HASH)
            }
            unsuccessful {
                github.commitStatus("failure", 'navikt/${APP_NAME}', $APP_TOKEN, $COMMIT_HASH)
            }
        }
    }
}