package ru.sfedu.buildingconstruction.util;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.sfedu.buildingconstruction.Constants;


public class ConfigurationXml {

    private static Logger log = Logger.getLogger(ConfigurationXml.class);
    private static String configPath;
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;
    private static Element el;
    private static NodeList nodeList;

    public static String getConfigPath() {

        return configPath;
    }

    public static void setConfigPath(String configPath) {
        ConfigurationXml.configPath = configPath;
        builder = null;
    }

    public static String getConfigurationValue(String key) {
        if (builder == null) {
            loadConfiguration();
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element && nodeList.item(i).getNodeName().equals(key)) {

                return nodeList.item(i).getTextContent();
            }
        }

        return null;
    }

    public static Map<String, String> getAllConfigurations() {

        Map<String, String> map = new HashMap<>();

        if (builder == null) {
            loadConfiguration();
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {

                map.put(nodeList.item(i).getNodeName(), nodeList.item(i).getTextContent());
            }
        }
        return map;

    }

    private static void loadConfiguration() {

        if (configPath == null) {
            configPath = Constants.PATH_TO_RESOURCES.concat(Constants.DEFAULT_XML_CONFIG_PATH);
        }

        log.info("ConfigPath = " + configPath);

        if (factory == null || builder == null) {
            factory = DocumentBuilderFactory.newInstance();
            try {
                builder = factory.newDocumentBuilder();
                document = builder.parse(new File(configPath));
                el = document.getDocumentElement();
                nodeList = el.getChildNodes();
            } catch (IOException | ParserConfigurationException | SAXException ex) {
                log.error(ex);
                System.exit(ex.hashCode());
            }

        }
    }

}
