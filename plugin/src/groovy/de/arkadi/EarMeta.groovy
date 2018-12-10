package de.arkadi

class EarMeta {

    def artifactSettings = {
        String version = '1.0'
        String group = 'de.arkadi'
        String displayName = 'flyway'
        String applicationName = "flyway"
        String description = "create deployable archive containing the flyJar"
        String javaVerion = '1.8'
        String buildDiractory = 'out'
        String debugLevel = "source,lines,vars"
        File libsDir = file('out/libs')
    }

    def descriptorSettings =deploymentDescriptor {
        applicationName = displayName
        displayName = displayName
        module(displayName+".jar", "java")
    }

}
