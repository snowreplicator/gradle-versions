package ru.zorro.gradle_versions.exceptions;

public class PropertiesFileNotFound extends RuntimeException {
    public PropertiesFileNotFound(Exception e) {
        super(e);
    }

    public PropertiesFileNotFound(String message) {
        super(message);
    }
}
