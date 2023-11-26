def call() {
    pipeline {
        agent any

        options {
            skipDefaultCheckout()
        }

        stages {
            stage('Stop running builds for the branch') {
                steps {
                    stopRunningBuilds()
                }
            }

            stage('Wipe workspace and checkout') {
                steps {
                    checkoutCurrentBranch()
                }
            }

            stage('Package and Push') {
                stages {
                    stage('Publish build check') {
                        steps {
                            publishChecks(
                                    name: 'Build image',
                                    title: 'Building image',
                                    status: 'IN_PROGRESS'
                            )
                        }
                    }

                    stage('Package') {
                        steps {
                            runWithMaven {
                                sh 'mvn clean package'
                            }
                        }
                    }

                    stage('Build docker image and push') {
                        steps {
                            buildAndPublish()
                        }
                    }
                }

                post {
                    always {
                        script {
                            def isBuildFailure = currentBuild.result == 'FAILURE'
                            def stageStatus = isBuildFailure ? 'FAILURE' : 'SUCCESS'
                            def message = isBuildFailure ?
                                    'Failed to build your image' :
                                    "Your image has been build and published: ${dockerUtils.getImageNameAndTag()}"

                            publishChecks(
                                    name: 'Build image',
                                    title: 'Building image',
                                    summary: message,
                                    status: 'COMPLETED',
                                    conclusion: stageStatus
                            )
                        }
                    }
                }
            }
        }
    }
}