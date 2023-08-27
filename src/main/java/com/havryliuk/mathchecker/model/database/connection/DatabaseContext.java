package com.havryliuk.mathchecker.model.database.connection;

import com.havryliuk.mathchecker.model.ResourceProperties;
import com.havryliuk.mathchecker.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class DatabaseContext {
    private static final Logger LOG = LogManager.getLogger(DatabaseContext.class);
    private static final Properties PROPERTIES;
    public static final String CONTEXT;
    public static final String SOURCE;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_CONTEXT);
        CONTEXT = (String) PROPERTIES.get("database.context");
        SOURCE = (String) PROPERTIES.get("database.source");
        LOG.debug("DatabaseContext initialised.");
    }
}
