package de.arkadi

import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

/**
 *  i extend the jar task function with my pre and post logic
 *  this task is populated with  settings
 */
class ServerJar extends Jar {
    String version = '1.1'
    String libName = "flyway"
    String classifire = 'migration'
    String group = 'Arkadi'
    String description = "create the flyway migration lib"
    File destination = new File('out/libs')
    String libDir='lib/'

    //TIP this part is == do first closure of a task
    @TaskAction
    def meFirst() {
        project.println("ServerJar Task: " + description)
    }

    ServerJar() {

        super.setDescription(description)
        super.setDestinationDir(destination)
        super.setBaseName(libName)
        super.setClassifier(classifire)
        super.setGroup(group)

        super.from(project.sourceSets.main.output.classesDirs) {
            include("de/**")
        }

        super.metaInf {
            from(project.sourceSets.main.output.resourcesDir) {
                exclude('xml')
                rename '(.*)_(.*).sql', '$2__$1.sql'
            }
            from(project.sourceSets.main.resources.files) {
                include("*.xml")
            }
        }
        super.manifest {
            attributes('Tool-Version': version)
            attributes('Description': description)
            attributes('Class-Path': project.configurations.earlib.files.collect { libDir + it.name }.join(' '))
        }

    }
}