package de.arkadi.initialisation;


import de.arkadi.migration.FlyWay;
import de.arkadi.migration.FlyWayTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import static de.arkadi.migration.FlyWayTarget.Target.*;
import static javax.ejb.TransactionManagementType.BEAN;

@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(BEAN)
public class ApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);


    @Inject
    @FlyWayTarget(BASELINE)
    FlyWay flywayBaseline;
    @Inject
    @FlyWayTarget(CORE)
    FlyWay flywayCore;
    @Inject
    @FlyWayTarget(PROJECT)
    FlyWay flywayProject;

    @PostConstruct
    private void init() {

        flywayBaseline.migrate();
        flywayCore.migrate();
        flywayProject.migrate();
    }

}