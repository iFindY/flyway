package de.arkadi.extenssions

import org.gradle.api.tasks.SourceSet

class ProjectStructure {

    def projectLayout =
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
