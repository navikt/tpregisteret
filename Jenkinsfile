@Library('peon-pipeline') _

node {
    def APP_NAME    = "tpregisteret"
    def APP_TOKEN   = github.generateAppToken()
    def DOCKER_REPO = "repo.adeo.no:5443"
    def COMMIT_HASH
    try {
        cleanWs()
        stage('checkout') {
            sh "git init"
            sh "git pull https://x-access-token:$APP_TOKEN@github.com/navikt/$APP_NAME.git"
            COMMIT_HASH = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
            github.commitStatus("pending", "navikt/$APP_NAME", $APP_TOKEN, $COMMIT_HASH)
        }
        stage('build') {
            sh '''docker run --rm -t
                    -v ${PWD}:/usr/src
                    -w /usr/src -u $(shell id -u)
                    -v ${HOME}/.m2:/var/maven/.m2
                    -e MAVEN_CONFIG=/var/maven/.m2
                    maven:3.5-jdk-11 mvn -Duser.home=/var/maven clean package -DskipTests=true -B -V
                '''
            sh '''docker run --rm -t
                    -v ${PWD}:/usr/src
                    -v /var/run/docker.sock:/var/run/docker.sock
                    -w /usr/src -u $(shell id -u)
                    -v ${HOME}/.m2:/var/maven/.m2
                    -e MAVEN_CONFIG=/var/maven/.m2
                    maven:3.5-jdk-11 mvn -Duser.home=/var/maven verify -B -e
                '''
        }
        stage('release') {
            withCredentials([usernamePassword(
                                credentialsId: 'nexusUploader',
                                usernameVariable: 'NEXUS_USERNAME',
                                passwordVariable: 'NEXUS_PASSWORD'
                            )]) {
                sh "docker login -u ${env.NEXUS_USERNAME} -p ${env.NEXUS_PASSWORD} $DOCKER_REPO"
                sh "docker push $DOCKER_REPO/$APP_NAME:$COMMIT_HASH"
            }
        }
        stage('deploy') {
            sh "script: sed \'s/latest/$COMMIT_HASH/\' nais.yaml | tee nais.yaml"
            sh "kubectl config use-context dev-fss"
            sh "kubectl apply -f nais.yaml --wait"
            sh "kubectl rollout status -w deployment/${APP_NAME}"
        }
        github.commitStatus("success", "navikt/${APP_NAME}", $APP_TOKEN, $COMMIT_HASH)
    } catch (err) {
        github.commitStatus("failure", "navikt/${APP_NAME}", $APP_TOKEN, $COMMIT_HASH)
    }
}
