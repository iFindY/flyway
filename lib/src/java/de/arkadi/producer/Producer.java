package de.arkadi.producer;

import de.arkadi.migration.FlywayMigration;
import de.arkadi.migration.Migration;
import de.arkadi.model.ApplicationProperties;
import de.arkadi.qualifier.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import static de.arkadi.qualifier.FlyWayTarget.Target.*;

@ApplicationScoped
public class Producer {

    @Inject
    ApplicationProperties applicationProperties;

    @Produces
    boolean enabled = Boolean.valueOf(System.getProperty("flyway", "false"));

    @Produces
    @Resource(lookup = "java:/PostgresDS")
    DataSource dataSource;

    @Produces
    @Named("FWCloader")
    ClassLoader cl = this.getClass().getClassLoader();

    @Produces
    public Logger produceLoggerSLF4J(InjectionPoint injectionPoint) {
        Class clazz = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(clazz);
    }


    @Produces
    @FlyWayTarget(CORE_BASELINE)
    @RequestScoped
    public Migration produceFlywayBaseline(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getCoreBaseline(), dataSource);
        return flyWay;
    }

    @Produces
    @FlyWayTarget(CORE_RELEASES)
    @RequestScoped
    public Migration produceFlywayCore(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getCoreReleases(), dataSource);
        return flyWay;
    }

    @Produces
    @FlyWayTarget(PROJECT_RELEASE)
    @RequestScoped
    public Migration produceFlywayProjectPost(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getProjectRelease(), dataSource);
        return flyWay;
    }

    //TODO disposers

}
