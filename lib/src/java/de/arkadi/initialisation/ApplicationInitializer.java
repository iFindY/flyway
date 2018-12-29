package de.arkadi.initialisation;


import de.arkadi.migration.Migration;
import de.arkadi.qualifier.FlyWayTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import static de.arkadi.qualifier.FlyWayTarget.Target.*;
import static javax.ejb.TransactionManagementType.BEAN;


@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(BEAN)
public class ApplicationInitializer {

    @Inject
    @FlyWayTarget(BASELINE)
    Migration flywayBaseline;

    @Inject
    @FlyWayTarget(CORE)
    Migration flywayCore;

    @Inject
    @FlyWayTarget(PROJECT)
    Migration flywayProject;


    @PostConstruct
    private void init() {
        flywayBaseline.migrate();
        flywayCore.migrate();
        flywayProject.migrate();
    }

}