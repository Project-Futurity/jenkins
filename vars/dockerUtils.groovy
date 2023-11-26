def getRegistry() {
    return 'oleksiihaidabrus'
}

def getFullImageName() {
    return "${getRegistry()}/${getImageNameAndTag()}"
}

def getImageNameAndTag() {
    return "${getImageName()}:${getImageTag()}"
}

def getImageName() {
    return mavenUtils.getServiceName()
}

def getImageTag() {
    return "${gitUtils.getCurrentBranch()}-${gitUtils.getCurrentCommitHash()}"
}