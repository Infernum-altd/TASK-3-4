package com.example.demo.utils;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

@Log
public class PropertyReader {
    public static String getProperty(String propertyName) {
        String readProperty = "";
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("/application.properties")) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            readProperty = prop.getProperty(propertyName);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }

        return readProperty;
    }
}
