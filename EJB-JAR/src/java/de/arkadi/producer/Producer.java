package de.arkadi.producer;

import de.arkadi.migration.Migration;
import de.arkadi.migration.FlywayMigration;
import de.arkadi.utils.FlyWayTarget;
import de.arkadi.model.ApplicationProperties;
import de.arkadi.utils.FlyWayTarget.Target;
import de.arkadi.utils.Flyway;
import de.arkadi.utils.LoggingUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.logging.Logger;

import static de.arkadi.utils.LoggingUtils.Type.*;

public class Producer {

    @Resource(name = "java:jboss/datasources/ArkadiDS")
    DataSource dataSource;

    @Inject
    ApplicationProperties applicationProperties;

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
        Class clazz = injectionPoint.getBean().getBeanClass();
        return LoggerFactory.getLogger(clazz);
    }


    @Produces
    @Flyway
    public Migration produceFlyway(@Any Instance<Migration> instance, InjectionPoint injectionPoint) {
        FlywayMigration flyWay = new FlywayMigration();
        Target TYPE = injectionPoint
                .getAnnotated()
                .getAnnotation(FlyWayTarget.class)
                .value();

        switch (TYPE) {
            case BASELINE:
                flyWay.setupFlyway(applicationProperties.getBaselineFlyway(), dataSource);
                break;
            case CORE:
                flyWay.setupFlyway(applicationProperties.getCoreFlyway(), dataSource);
                break;
            case PROJECT:
                flyWay.setupFlyway(applicationProperties.getProjectFlyway(), dataSource);
                break;
        }

        return flyWay;
    }

}
