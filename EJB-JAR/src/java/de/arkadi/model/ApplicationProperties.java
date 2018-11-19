package de.arkadi.model;

import de.arkadi.utils.LoggingUtils;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@ApplicationScoped
public class ApplicationProperties {

    private static String APPLICATION = "application.properties";
    private static String BASELINE_FLYWAY = "baselineFlyway.properties";
    private static String CORE_FLYWAY = "coreFlyway.properties";
    private static String PROJECT_FLYWAY = "projectFlyway.properties";

    private Properties application;
    private Properties baselineFlyway;
    private Properties coreFlyway;
    private Properties projectFlyway;


    @Inject
    @LoggingUtils
    private Logger LOGGER;

    @PostConstruct
    private void loadProperties() {
        application = load(APPLICATION);
        baselineFlyway = load(application.getProperty(BASELINE_FLYWAY));
        coreFlyway = load(application.getProperty(CORE_FLYWAY));
        projectFlyway = load(application.getProperty(PROJECT_FLYWAY));
    }

    public Properties getApplication() {
        return application;
    }

    public Properties getBaselineFlyway() {
        return baselineFlyway;
    }

    public Properties getCoreFlyway() {
        return coreFlyway;
    }

    public Properties getProjectFlyway() {
        return projectFlyway;
    }

    private Properties load(String property) {
        Properties temp = new Properties();
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("META-INF/properties/" + property)) {
            temp.load(input);
        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }
        return temp;
    }

}
