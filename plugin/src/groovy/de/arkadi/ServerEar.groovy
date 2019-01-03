package de.arkadi

import org.gradle.api.tasks.TaskAction
import org.gradle.plugins.ear.Ear

class ServerEar extends Ear {
    String version = '1.1'
    String libName = "wrapper"
    String description = "create delayable archive containing the flyJar"
    String group = "Arkadi"
    File destination = new File('out/libs')


    @TaskAction
    def meFirst() {
        project.println("ServerEar Task: " + description)
    }

    ServerEar() {
        super.setDescription(description)
        super.setDestinationDir(destination)
        super.setBaseName(libName)
        super.setGroup(group)

        super.from(project.tasks.getByName("ServerJar").archivePath)

        super.deploymentDescriptor {
            applicationName = libName
            displayName = libName
            //module("flyway-1.1-ejb.jar", "java")
        }
    }

}
