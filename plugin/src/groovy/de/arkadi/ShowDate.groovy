package de.arkadi

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ShowDate extends DefaultTask {
    String message = "Date is: "

    @TaskAction
    void showDate() {
        println message + new Date()
    }
}




