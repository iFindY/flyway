package de.arkadi.producer;

import de.arkadi.migration.Migration;
import de.arkadi.utils.*;
import de.arkadi.model.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.arkadi.migration.FlywayMigration;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.sql.DataSource;


import static de.arkadi.utils.FlyWayTarget.Target.*;


public class Producer {
    //java:jboss/datasources/ArkadiDS
    //name
    @Resource(lookup = "java:/iPIMDS")
    DataSource dataSource;

    @Inject
    ApplicationProperties applicationProperties;


    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        return dataSource;
    }


    @Produces
    @FWClassLoader
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
    @FlyWayTarget(BASELINE)
    public Migration produceFlywayBaseline(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getBaselineFlyway(), dataSource);
        return flyWay;
    }

    @Produces
    @RequestScoped
    @FlyWayTarget(CORE)
    public Migration produceFlywayCore(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getCoreFlyway(), dataSource);
        return flyWay;
    }

    @Produces
    @RequestScoped
    @FlyWayTarget(PROJECT)
    public Migration produceFlywayProject(FlywayMigration flyWay) {
        flyWay.setupFlyway(applicationProperties.getProjectFlyway(), dataSource);
        return flyWay;
    }


}
