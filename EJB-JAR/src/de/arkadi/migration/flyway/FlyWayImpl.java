package de.arkadi.migration.flyway;


import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@TransactionManagement(value = TransactionManagementType.BEAN)
public class FlyWayImpl implements FlyWay {

    @Resource
    DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyWayImpl.class);
    private Flyway flyway;
    private Properties flyWayProps;


    @PostConstruct
    public void init() {
        this.readProperties();
        this.setupFlyway();
    }

    // delete all data and set baseline
    @Override
    public void initialize() {
        LOGGER.warn("FlyWay-Migration: {}", "wiping schema");
        flyway.clean();
        LOGGER.warn("FlyWay-Migration: {}", "creating baseline");
        flyway.baseline();

    }

    // wipe database with all data
    @Override
    public void clean() {
        LOGGER.warn("FlyWay-Migration: {}", "wiping schema");
        flyway.clean();

    }

    // migrate new not applied sql scripts
    @Override
    public void migrate() {
        LOGGER.warn("FlyWay-Migration: {}", "pending migration");
        flyway.info().pending();
        LOGGER.warn("FlyWay-Migration: {}", "migration database");
        flyway.migrate();
        LOGGER.warn("FlyWay-Migration: {}", "applied migrations");
        flyway.info().applied();

    }

    // set current db state as base for future migrations
    @Override
    public void baseline() {
        LOGGER.warn("FlyWay-Migration: {}", "creating baseline");
        flyway.baseline();
    }

    // populate flyway with settings
    private void setupFlyway() {
        this.flyway = Flyway.configure()
                .dataSource(dataSource)
                .table(flyWayProps.getProperty("flyway.table"))
                .locations(flyWayProps.getProperty("flyway.locations"))
                .baselineDescription(flyWayProps.getProperty("flyway.locations"))
                .baselineVersion(flyWayProps.getProperty("flyway.baselineVersion"))
                .baselineOnMigrate(Boolean.valueOf(flyWayProps.getProperty("flyway.baselineOnMigrate")))
                .ignoreMissingMigrations(Boolean.valueOf(flyWayProps.getProperty("flyway.ignoreMissingMigrations")))
                .cleanOnValidationError(Boolean.valueOf(flyWayProps.getProperty("flyway.cleanOnValidationError")))
                .installedBy(flyWayProps.getProperty("flyway.installedBy"))
                .load();
    }

    // get schema version Table
    @Override
    public void info() {
        LOGGER.warn("FlyWay-Migration: {}", "applied, pending and current migrations");
        flyway.info();
    }


    // read property file
    private void readProperties() {
        flyWayProps = new Properties();
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("resources/properties/flyway.properties")) {
            flyWayProps.load(input);
            flyWayProps.forEach((x, y) -> LOGGER.warn("Flyway-Migration Settings: {} = {}", x, y));

        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }

    }


}
