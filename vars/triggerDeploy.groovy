import groovy.transform.Field

@Field def JOBS_MAP = [
        'api-gateway': 'Api Gateway Service Deploy',
        'futurity-telegram-bot': 'Futurity Telegram Bot Deploy',
        'project-service': 'Project Service Deploy',
        'notification-service': 'Notification Service Deploy',
        'auth-service': 'Auth Service Deploy'
]

def call() {
    def serviceName = dockerUtils.getImageName()
    def jobName = JOBS_MAP.get(serviceName)

    echo "Triggering job: ${jobName} with image ${serviceName}"

    build(
            job: jobName,
            wait: false,
            parameters: [
                    string(name: 'imageTag', value: dockerUtils.getImageTag()),
                    string(name: 'imageName', value: serviceName)
            ]
    )
}