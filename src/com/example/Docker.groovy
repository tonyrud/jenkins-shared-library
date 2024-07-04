#!/user/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.sh "docker build -t $imageName ."
        }

    // TODO: make credentialsId configurable
    def dockerLogin(String repoName) {
        script.withCredentials([script.usernamePassword(credentialsId: 'aws-ecr-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.sh "echo '${script.PASS}' | docker login -u '${script.USER}' --password-stdin $repoName"
        }
    }

    def dockerPush(String imageName) {
        script.sh "docker push $imageName"
    }
}
