package com.skillsdevelopment.sikuli.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DefaultImageElement implements ImageElement {

    private enum MouseEvent { LEFT_CLICK, RIGHT_CLICK, MIDDLE_CLICK, DOUBLE_CLICK }

    final private WebDriver driver;
    final private WebElement webElement;
    final private int x;
    final private int y;
    final private int width;
    final private int height;

    DefaultImageElement(WebDriver driver, WebElement webElement, int x, int y, int width, int height){
        this.driver = driver;
        this.webElement = webElement;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void click() {
        executeJavaScriptMouseAction(MouseEvent.LEFT_CLICK);
    }

    public void doubleClick() {
        executeJavaScriptMouseAction(MouseEvent.DOUBLE_CLICK);
    }

    public void rightClick() {
        executeJavaScriptMouseAction(MouseEvent.RIGHT_CLICK);
    }

    public void middleClick() {
        executeJavaScriptMouseAction(MouseEvent.MIDDLE_CLICK);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void executeJavaScriptMouseAction(MouseEvent type){
        int x = this.x + width/2;
        int y = this.y + height/2;

        switch (type) {
            case LEFT_CLICK:
                JavascriptExecutor.class.cast(driver).executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        +  x + ", " + y + ", " +
                        "false, false, false, false, 0, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case RIGHT_CLICK:
                JavascriptExecutor.class.cast(driver).executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        +  x + ", " + y + ", " +
                        "false, false, false, false, 2, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case MIDDLE_CLICK:
                JavascriptExecutor.class.cast(driver).executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        +  x + ", " + y + ", " +
                        "false, false, false, false, 1, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case DOUBLE_CLICK:
                JavascriptExecutor.class.cast(driver).executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('dblclick',true, true, window, 0, 0, 0, "
                        +  x + ", " + y + ", " +
                        "false, false, false, false, 0, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
        }
    }
}
