package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {


    void apply(Project project) {
        project.defaultTasks( 'flyEar')

        project.repositories {
            mavenCentral()
        }

        project.configurations {
            earlib
        }

        project.dependencies {
            compileOnly('javax:javaee-api:7.0')
            implementation('org.slf4j:slf4j-api:1.7.25')
            implementation('org.flywaydb:flyway-core:5.2.0')
            earlib('org.flywaydb:flyway-core:5.2.0')
        }

        project.sourceSets {
            main {
                java {
                    srcDirs = ['src/java']
                    outputDir = project.file('out/bin')
                }
                resources {
                    srcDirs = ['src/resources']
                }
            }

        }


        project.tasks.create("ServerJar", ServerJar)
        project.tasks.create("ServerEar", ServerEar)
        project.tasks.create("ServerClean", ServerClean)

        project.ServerEar.dependsOn(project.ServerClean, project.ServerJar)
        project.ServerClean.shouldRunAfter(project.ServerJar)
    }
}
