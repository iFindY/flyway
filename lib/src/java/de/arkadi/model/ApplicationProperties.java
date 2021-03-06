package de.arkadi.model;

import de.arkadi.utils.IOUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

/**
 * This class load properties needed for FlyWay startup and hold flyway specific information.
 * The BaselineVersion is automatically replaced wit the iPIM INSERT.sql script server.id  property.
 * If the script do not contain a version number the property setting is kept as default.
 **/
@ApplicationScoped
public class ApplicationProperties {
    private static String PROPERTIES_LOCATION = "META-INF/properties/flyway/";
    private static String CORE_BASELINE_PROPERTIES = "core-baseline.properties";
    private static String CORE_RELEASES_PROPERTIES = "core-release.properties";
    private static String PROJECT_PROPERTIES = "project-release.properties";

    private Properties coreBaseline;
    private Properties coreReleases;
    private Properties projectRelease;

    @Inject @Named("FWCloader")
    private ClassLoader classLoader;

    @Inject
    private IOUtils utils;

    @PostConstruct
    private void loadProperties() {
        coreBaseline = utils.load(PROPERTIES_LOCATION, CORE_BASELINE_PROPERTIES);
        coreReleases = utils.load(PROPERTIES_LOCATION, CORE_RELEASES_PROPERTIES);
        projectRelease = utils.load(PROPERTIES_LOCATION, PROJECT_PROPERTIES);

        coreReleases.replace("flyway.baselineVersion", baselineVersion());
    }

    private String baselineVersion() {
        String dir = coreBaseline.getProperty("flyway.locations").concat("/");
        String name = "iPIM INSERT";

        String file = utils.getPath(dir, name);
        String regEx = "v\\d+\\.\\d+\\.\\d+";

        return utils.searchVersion(file, regEx);
    }

    public Properties getCoreBaseline() {

        return coreBaseline;
    }

    public Properties getCoreReleases() {

        return coreReleases;
    }

    public Properties getProjectRelease() {
        return projectRelease;
    }
}