import groovy.transform.Field

@Field def WORKSPACE = '~/jenkins/workspace'
@Field def CONFIG_REPO = 'https://github.com/Project-Futurity/config'
@Field def PREPARE_SCRIPT = 'prepare_workspace.sh'
@Field def OVERRIDE_SCRIPT = 'override_compose.sh'

def prepareWorkspace(def sshContext) {
    sshScript remote: sshContext, script: PREPARE_SCRIPT
}

def clearWorkspace(def sshContext) {
    sshCommand remote: sshContext, command: "rm -r -f ${WORKSPACE}"
}

def upService(def sshContext, def imageName, def imageTag) {
    def config = [
            imageName: imageName,
            imageTag: imageTag,
            registry: dockerUtils.getRegistry()
    ]
    def configString = config.collect {k, v -> "$k=$v"}.join("\n")

    sshCommand remote: sshContext, command: "echo '${configString}' > ${WORKSPACE}/image_definition.txt"
    sshScript remote: sshContext, script: OVERRIDE_SCRIPT

    runDockerCompose(sshContext, imageName)
}

def runDockerCompose(def sshContext, def imageName) {
    def yamlFile = "${WORKSPACE}/docker-compose.yml"
    def overrideYamlFile = "${WORKSPACE}/docker-compose.override.yml"

    sshCommand(
            remote: sshContext,
            command: "docker compose --project-name \${PROJECT_NAME} -f ${yamlFile} -f ${overrideYamlFile} up -d ${imageName}"
    )
}