package de.arkadi

import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

/**
 *  i extend the jar task function with my pre and post logic
 *  this task is populated with  settings
 */
class ServerJar extends Jar {
    File libsDir = new File('out/libs')
    String version = '1.1'
    String description = "my jar for flyway"


    //TIP this part is == do first closure of a task
    @TaskAction
    def meFirst(){

    }

    //TIP main jar task, i hope
    ServerJar() {

        super.setDescription("create the flyway migration lib")
        super.setDestinationDir(libsDir)
        super.setBaseName('flyway')
        super.setClassifier('migration')
        super.setGroup("arkadi")

        super.from(sourceSets.main.output)

        super.metaInf {
            from(sourceSets.assets.resources) {
                exclude('xml')
                rename '(.*)_(.*).sql', '$2__$1.sql'
            }
            from(sourceSets.assets.resources.files) {
                include("*.xml")
            }
        }

        super.manifest {
            attributes('Tool-Version': version)
            attributes('Description': description)
            attributes('Class-Path': project.configurations.earlib.files.collect { 'lib/' + it.name }.join(' '))
        }

    }

}
