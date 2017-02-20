package com.seguriboxltvweb.jsf.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Esta clase contiene un método que obtiene el valor de la propiedad urlBase
 * que es donde se almacena la IP del SDK..
 *
 * @author Germán Hernández López
 */
public class SeguriboxGetProperties {

    String result = "";
    InputStream inputStream;

    public String getPropValues(String property) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            result = prop.getProperty(property);

        } catch (Exception e) {

        } finally {
            inputStream.close();
        }
        return result;
    }
}
