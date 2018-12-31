package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.bundling.Jar
import org.gradle.plugins.ear.Ear

// TIP A Project holds a Task-Container with Tasks -> a Task consist of -> Actions.
// TIP the Project.tasks property return a Task-Container with all tasks
// TIP you can ad "Actions" to a "Task" with doFirst() & doLast(). This take an Action or a Closure. Groovy-> Closure.
// TIP with doFirst- or doLast(Action) a Task is constructed which is chained with a the current Task.
// TIP  hmm -> doLast(action) append the action at the end of the TaskAction list.
class MyJarPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.defaultTasks('clean', 'flyEar')

        project.apply plugin: 'java-library'
        project.apply plugin: 'ear'

        project.ext {
            libsDir = project.file('out/libs')
            version = '1.1'
            description = "my jar for flyway"
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
            artifacts
            earlib
        }


        project.dependencies {
            compileOnly('javax:javaee-api:7.0')
            implementation('org.slf4j:slf4j-api:1.7.25')
            implementation('org.flywaydb:flyway-core:5.2.0')
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


        // TIP simple version to create a task, this one include configure without .configure
        project.task("flyJar", type: Jar, dependsOn: project.classes) {
            setDescription("create the flyway migration lib")
            setDestinationDir(project.ext.libsDir)
            setBaseName('flyway')
            setClassifier('ejb')
            setGroup("arkadi")

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

        // TIP different version to create a task. you can append <.configure{}>
        project.tasks.create("flyEar", Ear) {
            setDescription("create deployable archive containing the flyJar")
            setDestinationDir(project.ext.libsDir)
            setGroup("arkadi")

            //TIP get archive from task, not working know
            from project.tasks.getByName("flyJar").archivePath
            //from(project.configurations.artifacts)

            deploymentDescriptor {
                applicationName = "flyway"
                displayName = "flyway"
                module("flyway-1.1-ejb.jar", "java")
            }
        }

        // TIP if you configure this task
        project.tasks.create("flyClean", Delete) {
            setGroup("arkadi")
            setDescription("clean classes dir: " + project.getBuildDir().toString())
            delete(project.sourceSets.main.output.find { 'classes' })
        }

        //TIP configure existing tasks
        project.tasks.getByName("wrapper").configure {
            setGroup("arkadi")
            gradleVersion = '4.10.2'
            setDescription("set global gradle wrapper: " + gradleVersion)
        }

        //TIP zipTree() unpack jar file into a fileTree, know you can look and manipulate this files
        project.tasks.create("runtimeClasspath") {
            setGroup("arkadi")
            setDescription("print file on runtimeClasspath")
            project.configurations.runtimeClasspath
                    .collect { File file -> project.zipTree(file) }
                    .forEach { x -> x.getFiles() }
        }


        // TIP Hocks
        // TIP printing the dependency graph
        // TIP %s is a string placeholder
        // TIP example --> String s = "Monsterbacke";  o.printf( "|%20.7s|%n", s );  // |             Monster|
        // TIP you can do if  taskGraph.hasTask(flyJar) to set some properties, in the whenReady block
        // TIP you can taskGraph.before/afterTask{task->println task.name} // takes the current graph-task and do something
        project.gradle.taskGraph.whenReady {
            project.println(" Dependency Graph")
            project.gradle.taskGraph.allTasks.forEach {
                task -> project.printf("  > %-30s  %s%n", task, task.description)
            }
        }


        // TIP project.tasks.findAll{task-> task.startsWith('fly')} this return a list of tasks, you can depend on them.
        // TIP you can dependOn a coma separated list of tasks in one line
        // TIP this technique allow if statements if( bool){ construct graph }
        project.flyJar.dependsOn(project.wrapper)
        project.flyEar.dependsOn(project.flyClean, project.flyJar)
        project.flyClean.shouldRunAfter(project.flyJar)
    }
}
