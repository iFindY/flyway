package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // for testing
        project.task('showDate', type: ShowDate)

        // import needed plugins
        project.apply plugin: 'java'
        project.apply plugin: 'ear'

        // my task
        project.apply.task.create ('flywayJar',FlywayJar)



        project.task.getByName('Jar').configure {
            setDescription("create the flyway migration lib")
            setDestinationDir(libsDir)
            setBaseName('flyway')
            setClassifier('ejb')

            from(sourceSets.main.output)

        }
    }
}


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