package com.Orange.StepDefinitions;

import com.Orange.Factory.ReadConfigFiles;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseClass {

    public static WebDriver driver;

    public static void loadBrowser() {

        String browser = ReadConfigFiles.config.getProperty("browser");
        if(browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        else if(browser.equals("Edge")) {
            driver = new EdgeDriver();
        }
        else if(browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    public static void openUrl() {
        driver.get(ReadConfigFiles.config.getProperty("baseUrl"));
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void closeBrowser() {
        driver.quit();
    }



    /*
        All common Actions methods starts here.
     */
    public static WebElement element(String type, String value) {

        return switch (type) {
            case "id" -> driver.findElement(By.id(value));
            case "name" -> driver.findElement(By.name(value));
            case "className" -> driver.findElement(By.className(value));
            case "xpath" -> driver.findElement(By.xpath(value));
            case "css" -> driver.findElement(By.cssSelector(value));
            case "linkText" -> driver.findElement(By.linkText(value));
            case "partLinkText" -> driver.findElement(By.partialLinkText(value));
            default -> null;
        };
    }

    public static List<WebElement> elementList(String type, String value) {

        if(type.equals("id")) {
            return driver.findElements(By.id(value));
        }
        else if(type.equals("name")) {
            return driver.findElements(By.name(value));
        }
        else if(type.equals("className")) {
            return driver.findElements(By.className(value));
        }
        else if(type.equals("xpath")) {
            return driver.findElements(By.xpath(value));
        }
        else if(type.equals("css")) {
            return driver.findElements(By.cssSelector(value));
        }
        return null;
    }

    public static void clickButton(String type, String value) {

        switch (type) {
            case "id":
                driver.findElement(By.id(value)).click();
            case "name":
                driver.findElement(By.name(value)).click();
            case "className":
                driver.findElement(By.className(value)).click();
            case "css":
                driver.findElement(By.cssSelector(value)).click();
            case "linkText":
                driver.findElement(By.linkText(value)).click();
            case "partLinkText":
                driver.findElement(By.partialLinkText(value)).click();
            case "xpath":
                driver.findElement(By.xpath(value)).click();
        }
    }

    public static void enterText(String type, String value, String text) {

        switch (type) {
            case "css":
                driver.findElement(By.cssSelector(value)).sendKeys(text);
            case "id":
                driver.findElement(By.id(value)).sendKeys(text);
            case "name":
                driver.findElement(By.name(value)).sendKeys(text);
            case "className":
                driver.findElement(By.className(value)).sendKeys(text);
            case "xpath":
                driver.findElement(By.xpath(value)).sendKeys(text);
            default:
        }
    }

    public static Select selectDropDownBy(String type, String value) {

        return switch (type) {
            case "id" -> new Select(driver.findElement(By.id(value)));
            case "name" -> new Select(driver.findElement(By.name(value)));
            case "className" -> new Select(driver.findElement(By.className(value)));
            case "xpath" -> new Select(driver.findElement(By.xpath(value)));
            case "css" -> new Select(driver.findElement(By.cssSelector(value)));
            default -> null;
        };
    }

    public static void rightClick(String type, String value) {

        Actions action = new Actions(driver);
        switch (type) {
            case "css":
                action.contextClick(driver.findElement(By.cssSelector(value))).perform();
            case "id":
                action.contextClick(driver.findElement(By.id(value))).perform();
            case "name":
                action.contextClick(driver.findElement(By.name(value))).perform();
            case "className":
                action.contextClick(driver.findElement(By.className(value))).perform();
            case "xpath":
                action.contextClick(driver.findElement(By.xpath(value))).perform();
        }
    }

    public static void doubleClick(String type, String value) {

        Actions action = new Actions(driver);
        switch (type) {
            case "css":
                action.doubleClick(driver.findElement(By.cssSelector(value))).perform();
            case "id":
                action.doubleClick(driver.findElement(By.id(value))).perform();
            case "name":
                action.doubleClick(driver.findElement(By.name(value))).perform();
            case "className":
                action.doubleClick(driver.findElement(By.className(value))).perform();
            case "xpath":
                action.doubleClick(driver.findElement(By.xpath(value))).perform();
        }
    }

    public static void doDragDrop(WebElement source, WebElement target) {

        Actions action = new Actions(driver);
        action.dragAndDrop(source, target).perform();
    }

    public static void doDragDrop(WebElement source, int x, int y) {

        Actions action = new Actions(driver);
        action.dragAndDropBy(source, x, y).perform();
    }

    public static void moveCursor(WebElement target) {

        Actions action = new Actions(driver);
        action.moveToElement(target).perform();
    }

    public static void moveCursor(WebElement target, int x, int y) {

        Actions action = new Actions(driver);
        action.moveToElement(target, x, y).perform();
    }

    public static Actions doActions() {
        return new Actions(driver);
    }

    public static void wait_for_Element_until_display(int seconds, String xpath) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public static void pageScrollBottom() {
        JavascriptExecutor executor =(JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void pageScrollTop() {
        doActions().keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).perform();
    }

    public static void waitFewSeconds(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
