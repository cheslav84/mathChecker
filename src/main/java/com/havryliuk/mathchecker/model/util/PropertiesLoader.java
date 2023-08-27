package com.havryliuk.mathchecker.model.util;

import com.havryliuk.mathchecker.model.exceptions.PropertyInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads properties from file to Properties class.
 */
public class PropertiesLoader {
    private static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Method creates the instance of Properties and loads to it the properties from file.
     *
     * @param propertiesFileName String representing a propertiesFileName to properties file
     * @return Properties that loaded from corresponding file.
     * @throws PropertyInitializationException if the properties could not be loaded from file.
     */
    public static Properties getProperties(String propertiesFileName)
            throws PropertyInitializationException {
        Properties properties = new Properties();
        try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (is == null) {
                String message = "The properties file propertiesFileName haven't been found: " + propertiesFileName;
                LOG.error(message);
                throw new IOException(message);
            }
            properties.load(is);
        } catch (IOException e) {
            String message = "The properties file propertiesFileName haven't been found: " + propertiesFileName;
            LOG.error(message);
            throw new PropertyInitializationException(message);
        }
        LOG.info("Properties file \"{}\" is loaded.", propertiesFileName);
        return properties;
    }
}