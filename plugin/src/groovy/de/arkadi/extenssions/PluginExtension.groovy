package de.arkadi.extenssions

class PluginExtension {

    // naming
    String applicationName = "flyway"
    String version = '1.1'
    String classifier = "migration"
    String group = 'migration'

    //wrapper
    String gradleVersion = '5.2'
    // file locations
    File destinationDir = new File('out/libs')
    String earLibs = "flywayLib"


    // descriptions
    String jarDescription = "create the flyway migration lib"
    String cleanDescription = "clean classes dir: ${project.getBuildDir().toString()}"
    String earDescription = "create deployable archive containing $applicationName"
    String jarManifestDescription = "my jar for flyway"

    // dependencies
    def libs = []

}
