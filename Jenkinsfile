pipeline {
  agent any

  tools {
    gradle "gradle"
  }

  options { skipDefaultCheckout(false) }

  stages {
    stage('Preparation') {
      steps {
        script {
          env.ymd = sh (returnStdout: true, script: ''' echo date '+%Y%m%d-%H%M%S' ''')
        }
        echo("params : ${env.ymd} " + params.tag)
      }
    }

    stage('Build') {
      steps {
        sh "cd ./spring-cloud/backend && chmod +x gradlew && ./gradlew clean build"
        sh "cd ./spring-cloud/eureka && chmod +x gradlew && ./gradlew clean build"
        sh "cd ./spring-cloud/game && chmod +x gradlew && ./gradlew clean build"
        sh "cd ./spring-cloud/gateway && chmod +x gradlew && ./gradlew clean build"
      }
    }

    // stage('Docker') {
    //   steps {

    //   }
    // }

  }


}