package co.com.learningjava.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigReader.class
                .getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}