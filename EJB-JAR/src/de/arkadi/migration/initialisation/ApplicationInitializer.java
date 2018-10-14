package de.arkadi.migration.initialisation;


import de.arkadi.migration.flyway.FlyWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class ApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Inject
    private FlyWay flyway;

    // apply this method after construction
    @PostConstruct
    private void init() {
        LOGGER.info("FlyWay Migration triggered");
        flyway.migrate();
    }

}