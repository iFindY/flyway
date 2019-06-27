package de.arkadi


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.plugins.ear.Ear
import de.arkadi.extenssions.PluginExtension

class MyPlugin implements Plugin<Project> {


    void apply(Project project) {
        // entry point
        project.defaultTasks('ServerEar')
        project.buildDir='out'

        // plugins
        project.apply plugin: 'java-library'
        project.apply plugin: 'ear'

        // extension
        def props = project.extensions.findByType(PluginExtension.class)

        project.repositories {
            mavenCentral()
        }

        project.configurations { earlib }

        // TIP export
        project.dependencies {
            compileOnly('javax:javaee-api:7.0')
            implementation('org.slf4j:slf4j-api:1.7.25')
            implementation('org.flywaydb:flyway-core:5.2.0')
            earlib('org.flywaydb:flyway-core:5.2.0')
            deploy project(path: ':flyway', configuration: 'archives')
        }

        project.sourceSets {
            main {
                java {
                    srcDirs = ['src/java']
                }
                resources {
                    srcDirs = ['src/resources']
                }
            }
        }


        project.tasks.getByName("wrapper") {
            group = props.group
            gradleVersion = props.gradleVersion
            distributionType = Wrapper.DistributionType.ALL
        }

        project.tasks.create("flyJar", Jar) {
            // dependsOn
            dependsOn project.classes

            // properties
            archiveBaseName = props.applicationName
            archiveVersion = props.version
            archiveClassifier = props.classifier
            description = props.jarDescription
            destinationDir = props.destinationDir

            // set global lib name
            project.ext.jarArchiveName = archiveName

            // executable file selection
            from project.sourceSets.main.output.classesDirs include "de/**"

            // resources selection
            metaInf {
                from(project.sourceSets.main.output.resourcesDir) {
                    exclude('xml')
                    rename '(.*)_(.*).sql', '$2__$1.sql'
                }
                from(project.sourceSets.main.resources.files) {
                    include("*.xml")
                }
            }

            // create metadata and classpath extension
            manifest {
                attributes('Tool-Version': props.version)
                attributes('Description': props.jarManifestDescription)
                attributes('Class-Path': project.configurations.earlib.files.collect {
                    "$props.earLibs/" + it.name
                }.join(' '))
            }
        }


        project.tasks.create("FlyEar", Ear) {
            // dependsOn
            project.flyEar.dependsOn project.flyJar, project.flyClean

            // properties
            archiveBaseName = props.applicationName
            archiveVersion = props.version
            archiveClassifier = props.classifier
            description = props.earDescription
            group = props.group

            // resources
            libDirName = props.earLibs
            lib = project.configurations.earlib
            destinationDir = props.destinationDir

            // file selection
            from "$project.libsDir" include "*.jar"

            // application.xml creation
            deploymentDescriptor {
                applicationName = props.applicationName
                displayName = props.applicationName
                module(jarArchiveName, "java")
            }
        }

        project.tasks.create("FlyClean", Delete) {
            // dependsOn
            mustRunAfter project.flyJar

            // properties
            group = props.group
            description = props.cleanDescription

            // delete actions
            delete(project.sourceSets.main.output.classesDirs.getSingleFile())
            delete(project.sourceSets.main.output.resourcesDir)
        }

        project.ServerEar.dependsOn(project.ServerClean, project.ServerJar)
        project.ServerClean.shouldRunAfter(project.ServerJar)
    }
}
