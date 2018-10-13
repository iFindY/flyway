package de.arkadi.migration.initialisation;


import de.arkadi.migration.flyway.FlyWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    @EJB
    private FlyWay flyway;

    // apply this method after construction
    @PostConstruct
    private void init() {
        LOGGER.info("FlyWay Migration triggered");
        //      flyway.migrate();
    }

}