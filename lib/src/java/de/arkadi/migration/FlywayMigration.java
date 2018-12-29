package de.arkadi.migration;


import de.arkadi.qualifier.Loggable;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.transaction.Transactional.TxType.*;

@Loggable
@Transactional(SUPPORTS)
public class FlywayMigration implements Migration {

    private Flyway flyway;

    // delete all migration and set baseline
    public void initialize() {
        flyway.clean();
        flyway.baseline();
    }

    // wipe database with all migration
    public void clean() {
        flyway.clean();
    }

    // migrate new not applied sql scripts
    public String migrate() {
        return String.valueOf(flyway.migrate());
    }

    // set current db state as base for future migrations
    public void baseline() {
        flyway.baseline();
    }

    // get schema version Table
    public List info() {
        return Stream.of(flyway.info().all()).map(MigrationInfo::getDescription).collect(Collectors.toList());
    }

    // populate flyway with settings
    public void setupFlyway(Properties properties, DataSource dataSource) {
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
