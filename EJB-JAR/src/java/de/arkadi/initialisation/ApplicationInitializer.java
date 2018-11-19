package de.arkadi.initialisation;


import de.arkadi.migration.Migration;
import de.arkadi.utils.FlyWayTarget;
import de.arkadi.utils.Flyway;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import static de.arkadi.utils.FlyWayTarget.Target.*;
import static javax.ejb.TransactionManagementType.BEAN;


@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(BEAN)
public class ApplicationInitializer {

    @Inject
    @Flyway
    @FlyWayTarget(BASELINE)
    Migration flywayBaseline;

    @Inject
    @Flyway
    @FlyWayTarget(CORE)
    Migration flywayCore;

    @Inject
    @Flyway
    @FlyWayTarget(PROJECT)
    Migration flywayProject;



    @PostConstruct
    private void init() {
        flywayBaseline.migrate();
        flywayCore.migrate();
        flywayProject.migrate();
    }

}