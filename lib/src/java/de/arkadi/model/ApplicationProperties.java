package de.arkadi.model;

import de.arkadi.qualifier.FWClassLoader;
import de.arkadi.utils.IOUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;


@ApplicationScoped
public class ApplicationProperties {

    private static String PROPERTIES_LOCATION = "META-INF/properties/";
    private static String APPLICATION = "application.properties";
    private static String BASELINE_NAME = "baselineFlyway.properties";
    private static String CORE_NAME = "coreFlyway.properties";
    private static String PROJECT_NAME = "projectFlyway.properties";

    private Properties application;
    private Properties baselineFlyway;
    private Properties coreFlyway;
    private Properties projectFlyway;

    @Inject
    @FWClassLoader
    private ClassLoader classLoader;

    @Inject
    private IOUtils utils;

    @PostConstruct
    private void loadProperties() {
        application = utils.load(PROPERTIES_LOCATION, APPLICATION);
        baselineFlyway = utils.load(PROPERTIES_LOCATION, application.getProperty(BASELINE_NAME));
        coreFlyway = utils.load(PROPERTIES_LOCATION, application.getProperty(CORE_NAME));
        projectFlyway = utils.load(PROPERTIES_LOCATION, application.getProperty(PROJECT_NAME));

        baselineFlyway.replace("flyway.baselineVersion", updateVersion());
    }

    private String updateVersion() {
        String regEx = "\\d+\\.\\d+\\.\\d+";
        String path = utils.getPath("META-INF/sql/baseline/", "iPIM INSERT.sql");

        return utils.getValue(path, regEx);
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


}
