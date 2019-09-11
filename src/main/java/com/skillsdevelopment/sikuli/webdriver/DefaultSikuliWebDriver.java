package com.skillsdevelopment.sikuli.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.DefaultScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class DefaultSikuliWebDriver implements SikuliWebDriver {

    private static class WaitTimeOut {
        static final int DEFAULT_MILLISECONDS = 100000;
        static final int SHORT_MILLISECONDS = 1000;
        static final int DEFAULT_SECONDS = 60;
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebDriver delegate;

    ScreenRegion webdriverRegion;

    DefaultSikuliWebDriver(final WebDriver delegate) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        this.delegate = delegate;
        webdriverRegion = createWebDriverRegion();
    }

    @Override
    public ImageElement findImageElement(final URL imageUrl) {
        final ImageTarget target = new ImageTarget(imageUrl);

        waitForPageLoadingToComplete();

        final ScreenRegion imageRegion = findImage(target);

        if(imageRegion == null)
            return null;

        ScreenLocation center = imageRegion.getCenter();
        WebElement foundWebElement = findElementByLocation(center.getX(), center.getY());

        Rectangle r = imageRegion.getBounds();
        return new DefaultImageElement(delegate, foundWebElement,
                r.x,
                r.y,
                r.width,
                r.height);
    }

    @Override
    public WebElement findElementByLocation(int x, int y) {
        logger.debug("find element by location at {} {}", x, y);
        return (WebElement) executeScript("return document.elementFromPoint(" + x + " - window.pageXOffset," + (y - getPageYOffset()) + ");");
    }

    @Override
    public void setScreenSize(Dimension targetSize) {
        manage().window().setSize(targetSize);
        webdriverRegion = createWebDriverRegion();
    }

    @Override
    public Dimension getScreenSize() {
        return manage().window().getSize();
    }

    WebDriver getDelegate() {
        return delegate;
    }

    void waitForPageLoadingToComplete() {
        new WebDriverWait(delegate, WaitTimeOut.DEFAULT_SECONDS).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@javax.annotation.Nullable WebDriver driver) {
                return (Boolean) JavascriptExecutor.class.cast(driver).executeScript("return document.readyState == 'complete';");
            }
        });
    }

    long getDocumentHeight() {
        return (long) executeScript("" +
                "var d = document;\n" +
                "return Math.round(Math.max(\n" +
                "   d.body.scrollHeight, d.documentElement.scrollHeight,\n" +
                "   d.body.offsetHeight, d.documentElement.offsetHeight,\n" +
                "   d.body.clientHeight, d.documentElement.clientHeight\n" +
                "));");
    }

    void scrollToTop() {
        scrollTo(0, 0);
    }

    void scrollTo(int x, int y) {
        executeScript("window.scrollTo(" + x + "," + y + ");");
    }

    boolean isScrollAtPageBottom() {
        return (boolean) executeScript("" +
                "var body = document.body, html = document.documentElement;\n" +
                "var height = Math.max( body.scrollHeight, body.offsetHeight, \n" +
                "                       html.clientHeight, html.scrollHeight, html.offsetHeight ); \n" +
                "var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop); \n" +
                "return scrollTop >= height - window.innerHeight;");
    }

    long getPageYOffset() {
        return (long) executeScript("" +
                "var pageYOffset;" +
                "if(typeof(window.pageYOffset) == 'number') {" +
                "   pageYOffset = window.pageYOffset;" +
                "} else {" +
                "   pageYOffset = document.documentElement.scrollTop;" +
                "}" +
                "return Math.round(pageYOffset);");
    }

    long getWindowWidth() {
        return (long) executeScript("return Math.round(window.innerWidth);");
    }

    boolean takesFullPageScreenshots() throws IOException {
        BufferedImage fullScreenshot = ImageIO.read(getScreenshotAs(OutputType.FILE));
        double resizingFactor = calculateResizingFactor();
        return getDocumentHeight() / resizingFactor  <= fullScreenshot.getHeight();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return TakesScreenshot.class.cast(delegate).getScreenshotAs(target);
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return JavascriptExecutor.class.cast(delegate).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return JavascriptExecutor.class.cast(delegate).executeScript(script, args);
    }

    @Override
    public void get(String url) {
        delegate.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return delegate.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return delegate.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return delegate.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return delegate.findElement(by);
    }

    @Override
    public String getPageSource() {
        return delegate.getPageSource();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public void quit() {
        delegate.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return delegate.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return delegate.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return delegate.switchTo();
    }

    @Override
    public Navigation navigate() {
        return delegate.navigate();
    }

    @Override
    public Options manage() {
        return delegate.manage();
    }

    private ScreenRegion findImage(final ImageTarget target) {
        scrollToTop();

        ScreenRegion imageRegion;

        try {
            if(takesFullPageScreenshots())
                webdriverRegion = createWebDriverRegion();

            imageRegion = webdriverRegion.wait(target, WaitTimeOut.SHORT_MILLISECONDS);

            int targetHeight = target.getImage().getHeight();
            for (int currentVerticalLocation = 0; imageRegion == null && !isScrollAtPageBottom(); currentVerticalLocation += delegate.manage().window().getSize().getHeight()) {
                scrollTo(0, currentVerticalLocation - targetHeight);
                imageRegion = webdriverRegion.wait(target, WaitTimeOut.SHORT_MILLISECONDS);
            }
        } catch (IOException e) {
            throw new RuntimeException("unable to generate screenshots from driver");
        }

        if (imageRegion != null){
            Rectangle r = normalizeImageBounds(imageRegion);
            logger.debug("image is found at {} {} {} {}", r.x, r.y, r.width, r.height);
            scrollTo(0, (int) imageRegion.getBounds().getY());
        } else{
            logger.debug("image is not found");
            return null;
        }

        return imageRegion;
    }

    private ScreenRegion createWebDriverRegion() {
        WebDriverScreen webDriverScreen;
        try {
            webDriverScreen = new WebDriverScreen(this);
        } catch (IOException e) {
            throw new RuntimeException("unable to initialize Sikuli Web Driver Screen");
        }
        return new DefaultScreenRegion(webDriverScreen);
    }

    private Rectangle normalizeImageBounds(ScreenRegion imageRegion) {
        Rectangle r = imageRegion.getBounds();
        double resizingFactor = calculateResizingFactor();
        imageRegion.setBounds(new Rectangle(
                (int)(r.x * resizingFactor),
                (int)((r.y + (getPageYOffset() / resizingFactor)) * resizingFactor),
                (int)(r.width * resizingFactor),
                (int)(r.height * resizingFactor)));
        return imageRegion.getBounds();
    }

    private double calculateResizingFactor() {
        return getWindowWidth() / webdriverRegion.getBounds().getWidth();
    }
}
