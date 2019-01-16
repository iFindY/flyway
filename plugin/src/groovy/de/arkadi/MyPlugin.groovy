package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {


    void apply(Project project) {
        project.apply plugin: 'java-library'
        project.apply plugin: 'ear'

        project.defaultTasks('ServerEar')
        project.ext.earlib = "lib/"
        project.ext.projectStructure = new ProjectStructure()
        project.sourceSets { project.ext.projectStructure.getLayout() }

        project.repositories {
            mavenCentral()
        }

        project.configurations {
            earlib
        }

        // TIP export
        project.dependencies {
            compileOnly('javax:javaee-api:7.0')
            implementation('org.slf4j:slf4j-api:1.7.25')
            implementation('org.flywaydb:flyway-core:5.2.0')
            earlib('org.flywaydb:flyway-core:5.2.0')
        }


        project.tasks.create("ServerJar", ServerJar) {
            libDir = project.ext.earlib
        }
        project.tasks.create("ServerEar", ServerEar)
        project.tasks.create("ServerClean", ServerClean)

        project.ServerEar.dependsOn(project.ServerClean, project.ServerJar)
        project.ServerClean.shouldRunAfter(project.ServerJar)
    }
}
