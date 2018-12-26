package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.plugins.ear.Ear

class MyJarPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.apply plugin: 'java'
        project.apply plugin: 'ear'

        project.ext {
            libsDir = project.file('out/libs')
            version = '1.1'
            description="my jar for flyway"
        }


        project.setGroup('de.arkadi')
        project.setDescription('This is a migration prototype')
        project.setVersion(project.ext.version)
        project.setBuildDir('out')
        project.setDefaultTasks(['flyEar'])
        project.setSourceCompatibility('1.8')
        project.setTargetCompatibility('1.8')


        project.compileJava.options.debugOptions.debugLevel = "source,lines,vars"
        project.compileTestJava.options.debugOptions.debugLevel = "source,lines,vars"


        project.repositories {
            mavenCentral()
        }

        project.configurations {
            artifacts {}
        }


        project.dependencies {
            compileOnly('javax:javaee-api:7.0')
            compile('org.slf4j:slf4j-api:1.7.25')
            compile('org.flywaydb:flyway-core:5.2.0')
            earlib('org.flywaydb:flyway-core:5.2.0')
            artifacts project.fileTree(dir: project.libsDir, include: '*.jar')
        }

        project.sourceSets {
            main {
                java {
                    srcDirs = ['src/java']
                }
            }
            assets {
                resources {
                    srcDirs = ['src/resources']
                }
            }
        }



        project.compileJava {
            targetCompatibility = 1.8
            sourceCompatibility = 1.8
            options.deprecation = false
            options.warnings = false
            options.fork = true
            options.forkOptions.executable = 'javac'
            print options.compilerArgs

        }

        project.task("flyJar", type: Jar, dependsOn: project.classes) {
            setDescription("create the flyway migration lib")
            setDestinationDir(project.ext.libsDir)
            setBaseName('flyway')
            setClassifier('ejb')

            from(project.sourceSets.main.output)

            metaInf {
                from(project.sourceSets.assets.resources) {
                    exclude('xml')
                    rename '(.*)_(.*).sql', '$2__$1.sql'
                }
                from(project.sourceSets.assets.resources.files) {
                    include("*.xml")
                }
            }

            manifest {
                attributes('Tool-Version': project.ext.version)
                attributes('Description': project.ext.description)
                attributes('Class-Path': project.configurations.earlib.files.collect { 'lib/' + it.name }.join(' '))
            }

        }

        project.task("flyEar", type: Ear) {
            setDescription("create deployable archive containing the flyJar")
            setDestinationDir(project.ext.libsDir)
            from(project.configurations.artifacts)

            deploymentDescriptor {
                applicationName = "flyway"
                displayName = "flyway"
                module("flyway-1.1-ejb.jar", "java")
            }
        }

        project.task("flyClean", type: Delete) {
            delete(project.sourceSets.main.output.find { 'classes' })

        }

        project.task("wrapper", type: Wrapper) {
            gradleVersion = '4.10.2'
        }

        project.flyEar.dependsOn(project.flyJar)
        project.flyEar.dependsOn(project.flyClean)
        project.flyClean.shouldRunAfter(project.flyJar)


    }
}
