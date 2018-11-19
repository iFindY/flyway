package de.arkadi.producer;

import de.arkadi.migration.Migration;
import de.arkadi.migration.FlywayMigration;
import de.arkadi.utils.*;
import de.arkadi.model.ApplicationProperties;
import de.arkadi.utils.FlyWayTarget.Target;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static de.arkadi.utils.LoggingUtils.Type.*;

public class Producer {

    @Resource(name = "java:jboss/datasources/ArkadiDS")
    DataSource dataSource;

    @Inject
    ApplicationProperties applicationProperties;

    @Produces
    @ApplicationScoped
    List<Map<String, String>> dbVersion = new ArrayList<>();

    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        return dataSource;
    }

    @Produces
    @LoggingUtils(UTIL)
    public java.util.logging.Logger produceLoggerUtil(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @LoggingUtils(SLF4J)
    public org.slf4j.Logger produceLoggerSLF4J(InjectionPoint injectionPoint) {
        Class clazz = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(clazz);
    }


    @Produces
    @Flyway
    public Migration produceFlyway(FlywayMigration flywayMigration, InjectionPoint injectionPoint) {

        Target TYPE = injectionPoint
                .getAnnotated()
                .getAnnotation(FlyWayTarget.class)
                .value();

        switch (TYPE) {
            case BASELINE:
                flywayMigration.setupFlyway(applicationProperties.getBaselineFlyway(), dataSource);
                break;
            case CORE:
                flywayMigration.setupFlyway(applicationProperties.getCoreFlyway(), dataSource);
                break;
            case PROJECT:
                flywayMigration.setupFlyway(applicationProperties.getProjectFlyway(), dataSource);
                break;
        }
        return flywayMigration;
    }

}
