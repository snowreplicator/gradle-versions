package ru.zorro.gradle_versions;

import ru.zorro.gradle_versions.exceptions.PropertiesFileNotFound;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    public static final String BASE_DIR = "base_dir";

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILENAME = "application.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
            if (inputStream != null)
                PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesFileNotFound(e);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private PropertiesUtil() {
    }

}
