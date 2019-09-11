package com.skillsdevelopment.sikuli.webdriver;

import org.openqa.selenium.*;

import java.io.IOException;
import java.net.URL;

public interface SikuliWebDriver extends WebDriver, TakesScreenshot, JavascriptExecutor {
    public ImageElement findImageElement(final URL imageUrl) throws IOException;
    public WebElement findElementByLocation(int x, int y);
    public void setScreenSize(final Dimension targetSize);
    public Dimension getScreenSize();
}
