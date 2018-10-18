package de.arkadi.data.migration.flyway;


import de.arkadi.Interceptors.migration.Loggable;
import de.arkadi.producer.Produce;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@Loggable
@TransactionManagement(value = TransactionManagementType.BEAN)
public class FlyWayImpl implements FlyWay {

    @Resource
    DataSource dataSource;
    @Inject
    @Produce(Produce.Type.slf4j)
    private Logger LOGGER;
    //private static final Logger LOGGER = LoggerFactory.getLogger(FlyWayImpl.class);
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

    //TODO brich das auf einzelen methoden runter die du intern aufrufst damit AOP greift
    // migrate new not applied sql scripts
    @Override
    public void migrate() {
        LOGGER.warn("FlyWay-Migration: {}", "pending migration");
        Arrays.stream(flyway.info().pending()).map(MigrationInfo::getVersion).forEach(System.out::println);
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
                .installedBy(flyWayProps.getProperty("flyway.installedBy"))
                .baselineVersion(flyWayProps.getProperty("flyway.baselineVersion"))
                .sqlMigrationPrefix(flyWayProps.getProperty("flyway.sqlMigrationPrefix"))
                .baselineDescription(flyWayProps.getProperty("flyway.baselineDescription"))
                .baselineOnMigrate(Boolean.valueOf(flyWayProps.getProperty("flyway.baselineOnMigrate")))
                .ignoreMissingMigrations(Boolean.valueOf(flyWayProps.getProperty("flyway.ignoreMissingMigrations")))
                .cleanOnValidationError(Boolean.valueOf(flyWayProps.getProperty("flyway.cleanOnValidationError")))
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
                .getResourceAsStream("META-INF/properties/flyway.properties")) {
            flyWayProps.load(input);
            flyWayProps.forEach((x, y) -> LOGGER.warn("Flyway-Migration Settings: {} = {}", x, y));

        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }

    }


}
