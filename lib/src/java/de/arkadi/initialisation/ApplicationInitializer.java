package de.arkadi.initialisation;


import de.arkadi.migration.Migration;
import de.arkadi.qualifier.FlyWayTarget;
import org.slf4j.Logger;

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
    boolean enabled;

    @Inject
    Logger LOGGER;

    @Inject
    @FlyWayTarget(CORE_BASELINE)
    Migration coreBaseline;

    @Inject
    @FlyWayTarget(CORE_RELEASES)
    Migration coreRelease;

    @Inject
    @FlyWayTarget(PROJECT_RELEASE)
    Migration projectPostRelease;


    @PostConstruct
    private void init() {
        LOGGER.info("Flyway migration status : {}", enabled);
        if (enabled) migrate();

    }

    private void migrate() {
        coreBaseline.baseline();
        coreBaseline.migrate();

        coreRelease.baseline();
        coreRelease.migrate();

        projectPostRelease.baseline();
        projectPostRelease.migrate();
    }

}