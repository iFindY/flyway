package de.arkadi.initialisation;


import de.arkadi.data.migration.flyway.FlyWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

@Startup
@Singleton(mappedName = "StartUPBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Inject
    private FlyWay flyway;

    // apply this method after construction
    @PostConstruct
    private void init() {
        LOGGER.info("FlyWay Migration triggered");
        flyway.baseline();
        flyway.migrate();
    }

}