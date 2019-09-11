Sikuli - Selenium Web Driver Generator
=========================================
A library that generalizes sikuli and selenium web drivers. Developers can inject whatever webdriver that supports 
taking screenshots, and gets rewarded with image recognition attached ontop of the web driver. 

This small library can be seen as the glue between the following two:

* [Sikuli](https://code.google.com/p/sikuli-api/)
* [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

Sikuli Web Driver Generator adds functionality to existing Web Drivers to support Sikuli's ability for image recognition.
This provides you the ability to surf the web using only images as a source. No DOM parsing required. It also works in headless mode.

The library has been tested using the following:

* Selenium version 2.35.0
* [FirefoxDriver](https://code.google.com/p/selenium/wiki/FirefoxDriver) version 2.35.0
* [SafariDriver](https://code.google.com/p/selenium/wiki/SafariDriver) version 2.35.0
* [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/) version 2.4
* [InternetExplorerDriver] (https://code.google.com/p/selenium/wiki/InternetExplorerDriver) version 2.33.0
* [PhantomJSDriver](https://github.com/detro/ghostdriver) version 1.0.4
* Sony Vaio VPCZ1 - Windows 8
* MacBook Pro OS X 10.8.5

## Example of usage

### PhantomJS
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Chrome
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(ChromeDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Firefox
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(FirefoxDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Safari
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(SafariDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Internet Explorer
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(InternetExplorerDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>
