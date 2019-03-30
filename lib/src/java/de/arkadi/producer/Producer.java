package de.arkadi.producer;

import de.arkadi.migration.FlywayMigration;
import de.arkadi.migration.Migration;
import de.arkadi.model.ApplicationProperties;
import de.arkadi.qualifier.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import static de.arkadi.qualifier.FlyWayTarget.Target.*;


public class Producer {

    @Resource(lookup = "java:/iPIMDS-Postgres")
    DataSource dataSource;

    @Inject
    ApplicationProperties applicationProperties;


    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        return dataSource;
    }

    @Produces
    @Named("myClassloader")
    public ClassLoader produceClassLoader() {
        return this.getClass().getClassLoader();
    }


    @Produces
    public Logger produceLoggerSLF4J(InjectionPoint injectionPoint) {
        Class clazz = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(clazz);
    }


    @Produces
    @RequestScoped
    @FlyWayTarget(CORE_BASELINE)
    public Migration produceFlywayBaseline(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getCoreBaseline(), dataSource);
        return flyWay;
    }

    @Produces
    @RequestScoped
    @FlyWayTarget(CORE_RELEASES)
    public Migration produceFlywayCore(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getCoreReleases(), dataSource);
        return flyWay;
    }

    @Produces
    @RequestScoped
    @FlyWayTarget(PROJECT_POST)
    public Migration produceFlywayProjectPost(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getProjectReleasesPost(), dataSource);
        return flyWay;
    }

    @Produces
    @RequestScoped
    @FlyWayTarget(PROJECT_PRE)
    public Migration produceFlywayProjectPre(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getProjectReleasesPre(), dataSource);
        return flyWay;
    }

    //TODO disposers

}
