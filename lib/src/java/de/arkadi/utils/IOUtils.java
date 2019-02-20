package com.novomind.ipim.core.util.arkadi.utils;


import com.novomind.ipim.core.util.arkadi.qualifier.FWClassLoader;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class IOUtils {

    @Inject
    @FWClassLoader
    private ClassLoader classLoader;

    @Inject
    private Logger LOGGER;

    public Properties load(String base, String property) {
        Properties result = new Properties();
        try (InputStream input = classLoader.getResourceAsStream(base + property)) {
            result.load(input);

        } catch (IOException e) {
            LOGGER.error("can not find file => \n", e.getCause());
        }
        return result;
    }


    // helper method for .*filename.* search
    public String getPath(String directory, String file) {
        String path = directory;

        try (InputStream dir = classLoader.getResourceAsStream(directory)) {
            JarInputStream content = (JarInputStream) dir;

            JarEntry entry;
            while ((entry = content.getNextJarEntry()) != null) {
                path = entry.getName().contains(file) ? path.concat(entry.getName()) : path;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public String searchVersion(String file, String regEx) {
        String version = null;
        Pattern pattern = Pattern.compile(regEx);

        try (InputStream script = classLoader.getResourceAsStream(file)) {
            String lonString = new BufferedReader(new InputStreamReader(script))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Matcher matcher = pattern.matcher(lonString);
            matcher.find();
            version = matcher.group().substring(1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return version;
    }
}
