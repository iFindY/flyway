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
// TIP project.hasProperty(version) // do stuff   logger.info  version
// TIP setting.gradle can contain groovy loop code
// TIP Gradle Lifecycle:
// TIP Initialisation: gather information about all build participation projects (build.gradle's).
//      TIP this phase maps to the init.gradle + settings.gradle
//      TIP init.gradle setup environment variables,properties based on environment and personal information, repositories, register build listeners and loggers  ...
//      TIP settings.gradle determine which project include in a build.
//      TIP the settings.gradle file delegate object  is the "settings" object.
//      TIP Create an instance for each project. starts with init.gradle+other.gradle extensions +settings.gradle
//      TIP  init.gradle help setup build  environment d depending on the current machine. this one is inside .gradle/init.d
//      TIP the init.gradle file delegate object is the "gradle" object.
// TIP Configuration: in this phase, that configure created projects in the init-phase.
//      TIP  relay on build.gradle
//      TIP This read every build.gradle of a project. This build.gradle contain a configuration script.
//      TIP The delegate object is the project
//      TIP we write configuration scripts to do our stuff. For that we configure the delegate/project object
// TIP Execution: Build get performed, by performing a series of Tasks.
//      TIP relay on build.gradle
// TIP each Phase of gradle lifecycle pas to a gradle file
// TIP the delegate object og the script object is project, and the delegate object can change depending n the gradle lifecycle(init,config,exec)
//      TIP Configuration phase: build.gradle -> script.object -> delegate.object == project.object.
//      TIP Initialisation 2: settings.gradle -> script.object -> delegate.object == settings.object
//          TIP this object is multi-project oriented. you can access all project within a build.
//      TIP Initialisation 1: init.gradle -> script.object -> delegate.object == gradle.object
//            TIP here gradle do not know about any projects in the build, settings.gradle not read.
//      TIP Project.object -access-> gradle.object  || settings.object -access-> gradle.object
// TIP logger.info "build.gradle${project.relativePath(project.buildFile)}" // lots of helper methods
// TIP logger.info """ here a block of text is possible  """
// TIP !! in groovy ist return optional , the last line gets returned
// TIP !! def = Object;
// TIP    Object date = new Date();
// TIP    def date = new Date();
// TIP    can use Types to be safe
// TIP !! Type safety is only at runtime enforced
// TIP !! groovy generates getters and setter
// TIP !! you can access/create hashMaps with map.key

// TIP !! def hallo ={} is a closure == lambda or anonymous inner class , you can call this with hallo(). last line returns.
// TIP !! you can parameterize a closure  with hello{ a-> print a}  hello("arkadi")
// TIP    can be typed hello{String a-> print a}  hello("arkadi")
// TIP    closure without parameter implicitly take an untyped parameter "it"  hello{ print it}  hello("arkadi")
// TIP    def hello{} == Closure hello{...}
// TIP    ()  on methods are optional  halloMethod("arkadi") halloMethod "arkadi" ,if a closure is a parameter  helloMethod ({...}) or helloMethod {}
// TIP   more then one argument -> close comes last,  helloMethod 1,2,"arkadi", {...}
// TIP  closers keep reference to object where they have been defined p1.saynmae() == sayname()
// TIP delegate object == context
// TIP !!! Gradle magic def executeInside(Closure c){c.delegate=this;c();} //  closure passed to this methods ca access this class properties.


// TIP rootProject.children.each { subproject -> subproject.buildFileName = "${subproject.name}.gradle"} // rename default assumed build.gradle file name in  file name inside the subProject for the myProject.gradle.
// TIP you can run subprojct this will find root and do all needed stuff
// TIP apply plugin 'findbugs'

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
                    outputDir = project.file('out/bin')
                }
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
            options.debugOptions.debugLevel = "source,lines,vars"
            print options.compilerArgs
        }


        // TIP simple version to create a task, this one include configure without .configure
        project.task("flyJar", type: Jar, dependsOn: project.classes) {
            setDescription("create the flyway migration lib")
            setDestinationDir(project.ext.libsDir)
            setBaseName('flyway')
            setClassifier('ejb')
            setGroup("arkadi")

            from(project.sourceSets.main.output.classesDirs) {
                include("de/**")
            }

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
