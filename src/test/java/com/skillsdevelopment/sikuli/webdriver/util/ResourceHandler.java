package com.skillsdevelopment.sikuli.webdriver.util;

import java.io.InputStream;
import java.net.URL;

public class ResourceHandler {
    public static URL getResource(String resource) {
        if(OperatingSystem.isWindows()) {
            return ResourceHandler.class.getResource("/img/windows/" + resource);
        }
        if(OperatingSystem.isMac()) {
            return ResourceHandler.class.getResource("/img/mac/" + resource);
        }
        return null;
    }

    public static InputStream getIntegrationTestPropertiesFile() {
        return ResourceHandler.class.getClassLoader().getResourceAsStream("default-integration-test.properties");
    }
}
