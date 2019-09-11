package com.skillsdevelopment.sikuli.webdriver;

import com.skillsdevelopment.sikuli.webdriver.util.OperatingSystem;
import com.skillsdevelopment.sikuli.webdriver.util.ResourceHandler;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SikuliSafariWebDriverIT extends BaseIntegrationTest {

    @Test
    public void setScreenSize() {
        driver.setScreenSize(new Dimension(1280, 1024));
        assertThat(driver.getWindowWidth(), is(lessThanOrEqualTo(1280L)));
        assertThat(driver.getDocumentHeight(), is(lessThanOrEqualTo(1024L)));
    }

    @Test
    public void waitForPageToBeFullyLoaded() {
        driver.get(baseUrl + "/second-page.html");
        driver.waitForPageLoadingToComplete();
        assertThat((String) driver.executeScript("return document.readyState;"), is(equalTo("complete")));
    }

    @Test
    public void takingScreenShots() throws IOException {
        driver.setScreenSize(new Dimension(1280, 1024));
        driver.get(baseUrl + "/second-page.html");
        assertThat(driver.getDocumentHeight(), is(greaterThanOrEqualTo(1024L)));
        assertThat(driver.takesFullPageScreenshots(), is(false));
    }

    @Test
    public void findImageElement() throws IOException {
        URL coolSmiley = ResourceHandler.getResource("safari-cool-smiley.png");
        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
    }

    @Test
    public void surfUsingImageRecognition() throws IOException, InterruptedException {
        URL coolSmiley = ResourceHandler.getResource("safari-cool-smiley.png");
        URL superHero = ResourceHandler.getResource("safari-superhero.png");

        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
        element = driver.findImageElement(superHero);

        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
    }

    @Before
    public void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        assumeTrue(OperatingSystem.isMac());
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(SafariDriver.class).create();
        driver.setScreenSize(new Dimension(1280, 1024));
    }
}
