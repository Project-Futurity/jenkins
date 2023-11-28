def call(Closure body) {
    withCredentials([sshUserPrivateKey(credentialsId: 'futurity-ssh', keyFileVariable: 'identity', usernameVariable: 'userName')]) {
        def remote = [
                name         : 'futurity',
                host         : 'futurity.fun',
                allowAnyHosts: true,
                user         : userName,
                identityFile : identity
        ]

        body(remote)
    }
}