package com.skillsdevelopment.sikuli.webdriver;

import com.skillsdevelopment.sikuli.webdriver.util.ResourceHandler;
import org.junit.After;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Properties;

public abstract class BaseIntegrationTest {

    DefaultSikuliWebDriver driver;
    protected static String baseUrl;

    @BeforeClass
    public static void setupClass() throws IOException {
        Properties prop = new Properties();
        prop.load(ResourceHandler.getIntegrationTestPropertiesFile());
        baseUrl = prop.getProperty("baseUrl");
    }

    @After
    public void after() {
        if(driver != null)
            driver.quit();
    }
}
