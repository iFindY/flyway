package de.arkadi

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task('showDate', type: ShowDate)
        project.apply plugin: 'java'
        project.apply plugin: 'ear'
    }
}