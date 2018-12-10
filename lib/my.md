
task list() {
    doLast {
        println "classpath = ${configurations.artifacts.collect { File file -> file.name }}"
    }
}

task list2() {
    doLast {
        println "classpath = ${sourceSets.main.resources.collect { File file -> file.name }}"
    }
}

task list3() {
    doLast {
        println "classpath = ${sourceSets.main.output.resourcesDir.listFiles().collect { File file -> file.name }}"
    }
}

task list4() {
    doLast {
        println "classpath = ${buildDir.listFiles().collect { File file -> file.name }}"
    }
}

task list5() {
    doLast {
        println "classpath = ${sourceSets.main.output.resourcesDir}"
    }
}