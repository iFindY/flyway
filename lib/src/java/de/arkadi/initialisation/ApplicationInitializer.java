package com.novomind.ipim.core.util.arkadi.initialisation;


import com.novomind.ipim.core.util.arkadi.migration.Migration;
import com.novomind.ipim.core.util.arkadi.qualifier.FlyWayTarget;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import static com.novomind.ipim.core.util.arkadi.qualifier.FlyWayTarget.Target.*;
import static javax.ejb.TransactionManagementType.BEAN;


@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(BEAN)
public class ApplicationInitializer {

    @Inject
    Logger LOGGER;

    @Inject
    @FlyWayTarget(CORE_BASELINE)
    Migration coreBaseline;

    @Inject
    @FlyWayTarget(CORE_RELEASES)
    Migration coreRelease;

    @Inject
    @FlyWayTarget(PROJECT_PRE)
    Migration projectPreRelease;

    @Inject
    @FlyWayTarget(PROJECT_POST)
    Migration projectPostRelease;


    @PostConstruct
    private void init() {
        String enabled = System.getProperty("flyway", "false");
        if (Boolean.valueOf(enabled)) {

            coreBaseline.baseline();
            coreBaseline.migrate();

            projectPreRelease.baseline();
            projectPreRelease.migrate();

            coreRelease.baseline();
            coreRelease.migrate();

            projectPostRelease.baseline();
            projectPostRelease.migrate();
        }
        LOGGER.info("Flyway migration enabled : {}", enabled.toUpperCase());
    }

}