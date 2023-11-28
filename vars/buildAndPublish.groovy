def call() {
    docker.withRegistry('', 'docker-creds') {
        def imageName = dockerUtils.getFullImageName()
        def image = docker.build(imageName)

        echo "Pushing the image ${imageName}"
        image.push()
    }
}

