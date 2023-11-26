def getCurrentBranch() {
    return sh(
            script: 'git rev-parse --abbrev-ref HEAD',
            returnStdout: true
    ).trim()
}

def getCurrentCommitHash() {
    return sh(
            script: 'git rev-parse --short HEAD',
            returnStdout: true
    ).trim()
}