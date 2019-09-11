package com.skillsdevelopment.sikuli.webdriver;

import com.skillsdevelopment.sikuli.webdriver.util.OperatingSystem;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class SikuliWebDriverFactoryTest {

    DefaultSikuliWebDriver driver;

    @After
    public void cleanup() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void createSikuliPhantomJSDriver() {
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
        assertNotNull(driver);
        assertTrue(DefaultSikuliWebDriver.class.cast(driver).getDelegate() instanceof PhantomJSDriver);
    }

    @Test
    public void createSikuliChromeDriver() {
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(ChromeDriver.class).create();
        assertNotNull(driver);
        assertTrue(driver.getDelegate() instanceof ChromeDriver);
    }

    @Test
    public void createSikuliFirefoxDriver() {
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(FirefoxDriver.class).create();
        assertNotNull(driver);
        assertTrue(driver.getDelegate() instanceof FirefoxDriver);
    }

    @Test
    public void createSikuliSafariDriver() {
        assumeTrue(OperatingSystem.isMac());
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(SafariDriver.class).create();
        assertNotNull(driver);
        assertTrue(driver.getDelegate() instanceof SafariDriver);
    }

    @Test
    public void createSikuliInternetExplorerDriver() {
        assumeTrue(OperatingSystem.isWindows());

        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(InternetExplorerDriver.class).create();
        assertNotNull(driver);
        assertTrue(driver.getDelegate() instanceof InternetExplorerDriver);
    }

}
