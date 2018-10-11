package de.arkadi.migration.flyway;


import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@TransactionManagement(value = TransactionManagementType.BEAN)
public class FlyWayImpl implements FlyWay {

    @Resource
    DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyWayImpl.class);
    private Flyway flyway;
    private Properties flyWayProps;


    private FlyWayImpl() throws SQLException, IOException {
        this.flyWayProps = new Properties();
        this.flyway = new Flyway();
        this.readProperties();
        this.migrate();
        LOGGER.warn("Database '{}' successfully migrated", dataSource.getConnection().getMetaData().getDatabaseProductName());

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

    // get schema version Table
    @Override
    public void info() {
        LOGGER.warn("FlyWay-Migration: {}", "applied, pending and current migrations");
        flyway.info();
    }


    //TODO set baseline on migrate
    // read property file
    private void readProperties() throws IOException {
        InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("resources/properties/flyway.properties");
        flyWayProps.load(input);
        flyWayProps.forEach((x, y) -> LOGGER.warn("Flyway-Migration Settings: {} = {}", x, y));
        flyway.setDataSource(dataSource);
        flyway.configure(flyWayProps);
    }
}
