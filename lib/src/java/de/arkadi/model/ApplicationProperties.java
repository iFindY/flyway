package de.arkadi.model;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;


@ApplicationScoped
public class ApplicationProperties {

    private static String SQL_RESSOURCES = "META-INF/sql/";
    private static String APPLICATION = "application.properties";
    private static String BASELINE_FLYWAY = "baselineFlyway.properties";
    private static String CORE_FLYWAY = "coreFlyway.properties";
    private static String PROJECT_FLYWAY = "projectFlyway.properties";

    private Properties application;
    private Properties baselineFlyway;
    private Properties coreFlyway;
    private Properties projectFlyway;


    @Inject
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
        Properties result = new Properties();
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("META-INF/properties/" + property)) {

            result.load(input);

        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }
        return result;
    }

    private String getVersion() {

        try {
            Enumeration<URL> temp = Thread.currentThread().getContextClassLoader().getResources(SQL_RESSOURCES);
            String script = Collections.list(temp).stream()
                    .filter(x -> x.getFile().contains("INSERT.sql"))
                    .findFirst()
                    .get()
                    .getFile();

            InputStream input = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(script);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
