package de.arkadi

import org.gradle.api.tasks.SourceSet

class ProjectStructure {

    SourceSet projectLayout =
            main {
                java {
                    srcDirs = ['src/java']
                    outputDir = project.file('out/bin')
                }
                resources {
                    srcDirs = ['src/resources']
                }
            }


    SourceSet getLayout() {
        return projectLayout
    }

    void setLayout(SourceSet layout) {
        this.projectLayout = layout
    }
}
