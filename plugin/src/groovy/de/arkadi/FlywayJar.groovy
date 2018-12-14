package de.arkadi

import org.gradle.api.Action
import org.gradle.api.java.archives.Manifest
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar


class FlywayJar extends Jar{

  FlywayJar(){
      // lol man kann über den konstruktor die jar setten
      description ="hallo"
      metaInf {}

  }
    def artifactSettings = {
        String version = '1.1'
        String group = 'de.arkadi'
        String baseName = 'flyway'
        String description = "create the flyway migration lib"
        String javaVerion = '1.8'
        String buildDiractory = 'out'
        String debugLevel = "source,lines,vars"
        File libsDir = file('out/libs')
    }


    def compilerSettings = compileJava {
        targetCompatibility = 1.8
        sourceCompatibility = 1.8
        options.deprecation = false
        options.warnings = false
        options.fork = true
        options.forkOptions.executable = 'javac'
        print options.compilerArgs

    }

    def metaInfSettings = metaInf {
        from(sourceSets.assets.resources) {
            exclude('xml')
            rename '(.*)_(.*).sql', '$2__$1.sql'
        }
        from(sourceSets.assets.resources.files) {
            include("*.xml")
        }
    }

    def manfisetSettings = manifest {
        attributes('Tool-Version': this.version)
        attributes('Description': this.description)
        attributes('Class-Path': configurations.earlib.files.collect { 'lib/' + it.name }.join(' '))
    }


    @TaskAction
    void flywayjar() {
        task flyJar(type: Jar, dependsOn: classes) {
            setDescription("create the flyway migration lib")
            setDestinationDir(libsDir)
            setBaseName('flyway')
            setClassifier('ejb')

            from(sourceSets.main.output)

            metaInf {
                from(sourceSets.assets.resources) {
                    exclude('xml')
                    rename '(.*)_(.*).sql', '$2__$1.sql'
                }
                from(sourceSets.assets.resources.files) {
                    include("*.xml")
                }
            }

            manifest {
                attributes('Tool-Version': this.version)
                attributes('Description': this.description)
                attributes('Class-Path': configurations.earlib.files.collect { 'lib/' + it.name }.join(' '))
            }

        }
    }

}
