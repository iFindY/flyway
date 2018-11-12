package de.arkadi.producer;

import de.arkadi.migration.FlyWay;
import de.arkadi.migration.FlyWayImpl;
import de.arkadi.migration.FlyWayTarget;
import de.arkadi.model.ApplicationProperties;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.logging.Logger;

import static de.arkadi.migration.FlyWayTarget.Target.*;
import static de.arkadi.producer.LoggingUtils.Type.*;



public class Producer {

    @Resource(name = "java:jboss/datasources/ArkadiDS")
    DataSource dataSource;

    @Inject
    ApplicationProperties applicationProperties;

    @Produces
    public DataSource dataSource() {
        return dataSource;
    }

    @Produces
    @LoggingUtils(UTIL)
    public Logger produceLoggerUtil(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @LoggingUtils(SLF4J)
    public org.slf4j.Logger produceLoggerSLF4J(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
    }

    @Produces
    @FlyWayTarget(BASELINE)
    public FlyWay produceFlywayBaseline() {
        FlyWayImpl flyWay = new FlyWayImpl();
        flyWay.setupFlyway(applicationProperties.getBaselineFlyway(), dataSource);
        return flyWay;
    }

    @Produces
    @FlyWayTarget(CORE)
    public FlyWay produceFlywayCore() {
        FlyWayImpl flyWay = new FlyWayImpl();
        flyWay.setupFlyway(applicationProperties.getCoreFlyway(), dataSource);
        return flyWay;
    }

    @Produces
    @FlyWayTarget(PROJECT)
    public FlyWay produceFlywayProject() {
        FlyWayImpl flyWay = new FlyWayImpl();
        flyWay.setupFlyway(applicationProperties.getProjectFlyway(), dataSource);
        return flyWay;
    }

}
