package de.arkadi.initialisation;


import de.arkadi.data.migration.flyway.FlyWay;
import de.arkadi.data.migration.flyway.FlyWayImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
class ApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    private FlyWay flywayCore = FlyWayImpl.create("coreFlyway.property");
    private FlyWay flywayProject = FlyWayImpl.create("showFlyway.property");


    // apply this method after construction
    @PostConstruct
    private void init() {
        LOGGER.info("FlyWay Migration triggered");
        // order is important
        flywayCore.baseline();
        flywayCore.migrate();

        // order is important
        flywayProject.baseline();
        flywayProject.migrate();
    }

}