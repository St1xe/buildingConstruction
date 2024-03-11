/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.sfedu.buildingconstruction.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import ru.sfedu.buildingconstruction.Constants;

/**
 *
 * @author maksim
 */
public class ConfigurationUtil {

    private static Logger log = Logger.getLogger(ConfigurationUtil.class);
    private static String configPath;
    private static Properties properties = new Properties();

    public static String getConfigPath() {

        return configPath;
    }

    public static void setConfigPath(String configPath) {
        ConfigurationUtil.configPath = configPath;
    }

    public static String getConfigurationValue(String key) {
        if (properties.isEmpty()) {
            loadConfiguration();

        }
        return properties.getProperty(key);
    }

    public static Properties getAllConfigurations() {
        if (properties.isEmpty()) {
            loadConfiguration();
        }
        return properties;
    }

    private static void loadConfiguration() {

        if (configPath == null) {
            checkConfigurationPath();
        }
        
        log.info("loadConfiguration [1]: ConfigPath = " + configPath);

        try (InputStream in = new FileInputStream(Constants.PATH_TO_RESOURCES.concat(configPath))) {

            try {
                properties.load(in);
            } catch (IOException ex) {
                log.error("loadConfiguration [2]: " + ex.getMessage());
            }

        } catch (IOException ex) {
            log.error("loadConfiguration [3]: " + ex.getMessage());
        }

    }

    private static void checkConfigurationPath() {

        if (System.getProperty(Constants.PATH) != null) {
            configPath = System.getProperty(Constants.PATH);
        } else {
            configPath = Constants.DEFAULT_PROPERTY_CONFIG_PATH;
        }
    }

}
