import groovy.transform.Field

@Field def JOBS_MAP = [
        'api-gateway': 'Api Gateway Service Deploy'
]

def call() {
    def serviceName = mavenUtils.getServiceName()
    def jobName = JOBS_MAP.get(serviceName)

    echo "Triggering job: ${jobName}"

    build(
            job: jobName,
            wait: false,
            parameters: [
                    string(name: 'imageTag', value: dockerUtils.getImageTag()),
                    string(name: 'imageName', value: serviceName)
            ]
    )
}