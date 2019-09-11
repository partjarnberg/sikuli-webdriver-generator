package com.skillsdevelopment.sikuli.webdriver;

import com.skillsdevelopment.sikuli.webdriver.util.ResourceHandler;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SikuliPhantomJSWebDriverIT extends BaseIntegrationTest {

    @Test
    public void setScreenSize() {
        driver.setScreenSize(new Dimension(1280, 1024));
        assertThat(driver.getWindowWidth(), is(equalTo(1280L)));
        assertThat(driver.getDocumentHeight(), is(equalTo(1024L)));
    }

    @Test
    public void waitForPageToBeFullyLoaded() {
        driver.get(baseUrl + "/second-page.html");
        driver.waitForPageLoadingToComplete();
        assertThat((String)driver.executeScript("return document.readyState;"), is(equalTo("complete")));
    }

    @Test
    public void takingScreenShots() throws IOException {
        driver.setScreenSize(new Dimension(1280, 1024));
        driver.get(baseUrl + "/second-page.html");
        assertThat(driver.getDocumentHeight(), is(greaterThanOrEqualTo(1024L)));
        assertThat(driver.takesFullPageScreenshots(), is(true));
    }

    @Test
    public void findImageElement() throws IOException {
        URL coolSmiley = ResourceHandler.getResource("phantomjs-cool-smiley.jpg");
        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
    }

    @Test
    public void surfUsingImageRecognition() throws IOException, InterruptedException {
        URL coolSmiley = ResourceHandler.getResource("phantomjs-cool-smiley.jpg");
        URL superHero = ResourceHandler.getResource("phantomjs-superhero.png");

        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
        driver.waitForPageLoadingToComplete();

        element = driver.findImageElement(superHero);
        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
    }

    @Before
    public void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        driver = (DefaultSikuliWebDriver) SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
        driver.setScreenSize(new Dimension(1280, 1024));
    }
}
