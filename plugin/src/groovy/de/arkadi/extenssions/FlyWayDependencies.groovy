package de.arkadi.extenssions

import org.gradle.api.artifacts.Configuration

class FlyWayDependencies {

    Configuration implementation = 'javax:javaee-api:7.0'
    def compileDeps = []
    def compileOnly = ['javax:javaee-api:7.0']
    def implementation2 = ['org.slf4j:slf4j-api:1.7.25', 'org.flywaydb:flyway-core:5.2.0']
    def api = []
    def earLib = ['org.flywaydb:flyway-core:5.2.0']

}


