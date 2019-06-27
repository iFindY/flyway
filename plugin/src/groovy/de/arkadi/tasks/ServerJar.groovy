package de.arkadi.tasks

import de.arkadi.extenssions.ProjectStructure
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

/**
 *  i extend the jar task function with my pre and post logic
 *  this task is populated with  settings
 *   org.gradle.jvm.tasks.Jar
 */
class ServerJar extends Jar {

    Properties props = new Properties()

    @TaskAction
    def meFirst() {
        ProjectStructure projectStructure = new ProjectStructure()
        project.sourceSets {
            projectStructure.getLayout()
        }
    }

    ServerJar() {
        archiveClassifier = props.classifier
        description = props.description
        archiveDestinationDirectory = props.destination
        archiveBaseName = props.libName
        group = props.group

        from project.sourceSets.main.output.classesDirs include("de/**")

        metaInf {
            from(project.sourceSets.main.output.resourcesDir) {
                exclude('xml')
                rename '(.*)_(.*).sql', '$2__$1.sql'
            }
            from(project.sourceSets.main.resources.files) {
                include("*.xml")
            }
        }
        manifest {
            attributes('Tool-Version': props.version)
            attributes('Description': description)
            attributes('Class-Path': project.configurations.earlib.files.collect { props.libDir + it.name }.join(' '))
        }

    }
}