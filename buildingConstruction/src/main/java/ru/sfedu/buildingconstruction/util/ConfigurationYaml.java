package ru.sfedu.buildingconstruction.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Map;

import org.apache.log4j.Logger;

import org.yaml.snakeyaml.Yaml;

import ru.sfedu.buildingconstruction.Constants;

/**
 *
 * @author maksim
 */
public class ConfigurationYaml {

    private static Logger log = Logger.getLogger(ConfigurationYaml.class);
    private static String configPath;
    private static Yaml yaml = new Yaml();
    private static FileReader fr;
    private static Map<String, String> map;

    public static String getConfigPath() {

        return configPath;
    }

    public static void setConfigPath(String configPath) {
        ConfigurationYaml.configPath = configPath;
        fr = null;
    }

    public static String getConfigurationValue(String key) {
        return getAllConfigurations().get(key);
    }

    public static Map<String, String> getAllConfigurations() {
        loadConfiguration();
        return map;

    }

    private static void loadConfiguration() {

        if (configPath == null) {
            configPath = Constants.PATH_TO_RESOURCES.concat(Constants.DEFAULT_YAML_CONFIG_PATH);
        }

        if (fr == null) {
            try {
                fr = new FileReader(configPath);
                map = yaml.load(fr);
            } catch (FileNotFoundException ex) {
                log.error(ex);
                System.exit(1);
            } 
        }

    }

}
