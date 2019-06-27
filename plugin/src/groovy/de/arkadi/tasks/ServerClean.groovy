package de.arkadi.tasks

import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

class ServerClean extends Delete {

    String description = "clean binary dir: " + project.sourceSets.main.output.classesDirs.getAsPath()
    String group = "Arkadi"

    @TaskAction
    def doFirst() {
        project.println("ServerClean Task: " + description)
    }

    ServerClean() {
        super.setGroup(group)
        super.setDescription(description)
        super.delete(project.sourceSets.main.output.classesDirs)
    }
}
