def call() {
    docker.withRegistry('', 'docker-creds') {
        def image = docker.build(dockerUtils.getFullImageName())
        image.push()
    }
}

