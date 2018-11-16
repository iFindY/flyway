package de.arkadi.migration;


import de.arkadi.utils.Loggable;
import de.arkadi.utils.LoggingUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;

import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

import static javax.ejb.TransactionManagementType.BEAN;

@Loggable
@TransactionManagement(BEAN)
public class FlywayMigration implements Migration {


    @Inject
    @LoggingUtils
    private Logger LOGGER;

    private Flyway flyway;


    // delete all migration and set baseline
    @Override
    public void initialize() {
        flyway.clean();
        flyway.baseline();
    }

    // wipe database with all migration
    @Override
    public void clean() {
        flyway.clean();

    }

    // migrate new not applied sql scripts
    @Override
    public void migrate() {
        flyway.migrate();
        flyway.info().applied();
    }

    // set current db state as base for future migrations
    @Override
    public void baseline() {
        flyway.baseline();
    }

    // get schema version Table
    @Override
    public void info() {
        flyway.info();
    }

    // populate flyway with settings
    public void setupFlyway(Properties properties,DataSource dataSource) {
        this.flyway = Flyway.configure()
                .dataSource(dataSource)
                .table(properties.getProperty("flyway.table"))
                .locations(properties.getProperty("flyway.locations"))
                .installedBy(properties.getProperty("flyway.installedBy"))
                .baselineVersion(properties.getProperty("flyway.baselineVersion"))
                .sqlMigrationPrefix(properties.getProperty("flyway.sqlMigrationPrefix"))
                .baselineDescription(properties.getProperty("flyway.baselineDescription"))
                .baselineOnMigrate(Boolean.valueOf(properties.getProperty("flyway.baselineOnMigrate")))
                .ignoreMissingMigrations(Boolean.valueOf(properties.getProperty("flyway.ignoreMissingMigrations")))
                .cleanOnValidationError(Boolean.valueOf(properties.getProperty("flyway.cleanOnValidationError")))
                .load();
    }
}
