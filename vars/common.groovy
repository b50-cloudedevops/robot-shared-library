def sonarCheck() {
    sh '''
          sonar-scanner -Dsonar.host.url=http://172.31.1.192:9000/  -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.java.binaries=target/
          sonar-quality-gate ${SONAR_USR} ${SONAR_PSW} 172.31.1.192 ${COMPONENT} || true // This gives the result of the scan based on that it will abort pipeline or proceed further    
          echo sonarchecks for ${COMPONENT}    
    '''
}

//For non-Java, code source parameter is -Dsonar.sources=.
//for java, code source parameter is -Dsonar.sources= target/