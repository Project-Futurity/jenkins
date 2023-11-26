def getServiceName() {
    runWithMaven() {
        def serviceName = sh(
                script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout',
                returnStdout: true
        )

        return serviceName
    }
}