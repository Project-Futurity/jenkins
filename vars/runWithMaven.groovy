def call(Closure body) {
    withMaven(
            maven: 'maven-3',
            options: [
                    findbugsPublisher(disabled: true),
                    jgivenPublisher(disabled: true),
                    mavenLinkerPublisher(disabled: true),
                    openTasksPublisher(disabled: true),
                    junitPublisher(disabled: true)
            ]
    ) {
        body()
    }
}