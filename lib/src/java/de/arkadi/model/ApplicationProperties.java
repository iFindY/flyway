package de.arkadi.model;

import de.arkadi.utils.FWClassLoader;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.jar.JarInputStream;


@ApplicationScoped
public class ApplicationProperties {

    private static String SQL_RESSOURCES = "META-INF/sql/baseline/";
    private static String PROPERTIES_LOCATION = "META-INF/properties/";
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

    @Inject
    @FWClassLoader
    private ClassLoader classLoader;

    @PostConstruct
    private void loadProperties() {
        application = load(APPLICATION);
        baselineFlyway = load(application.getProperty(BASELINE_FLYWAY));
        coreFlyway = load(application.getProperty(CORE_FLYWAY));
        projectFlyway = load(application.getProperty(PROJECT_FLYWAY));
        baselineFlyway.setProperty("flyway.baselineVersion", getVersion());
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
        try (InputStream input = classLoader.getResourceAsStream(PROPERTIES_LOCATION + property)) {
            result.load(input);

        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }
        return result;
    }

    private String getVersion() {
        String path = SQL_RESSOURCES;

        try (InputStream dir = classLoader.getResourceAsStream(SQL_RESSOURCES)) {
            JarInputStream content = (JarInputStream) dir;

            String entry;
            while (Objects.nonNull(entry = content.getNextJarEntry().getName())) {
                path = entry.contains("iPIM INSERT.sql") ? path.concat(entry) : path;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


}
