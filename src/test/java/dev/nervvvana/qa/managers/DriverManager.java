package dev.nervvvana.qa.managers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private WebDriver driver;

    private static DriverManager INSTANCE = null;

    private final PropertyManager props = PropertyManager.getPropertyManager();

    private DriverManager() {
    }

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initDriver() {
        if ("remote".equals(props.getProperty("browser.type"))) {
            initRemoteDriver();
        } else if ("local".equals(props.getProperty("browser.type"))) {
            switch (props.getProperty("browser.name")) {
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", props.getProperty("gecko.path"));
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", props.getProperty("chrome.path"));
                    driver = new ChromeDriver();
                    break;
                default:
                    Assertions.fail("Типа браузера '" + props.getProperty("browser.name") + "' не существует во фреймворке");
            }
        }
    }

    private void initRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();
        capabilities.setCapability("browserName", props.getProperty("browser.name"));
        capabilities.setCapability("browserVersion", "109.0");
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);
        capabilities.setCapability("selenoid:options", selenoidOptions);

        try {
            driver = new RemoteWebDriver(
                    URI.create(props.getProperty("selenoid.url")).toURL(),
                    capabilities
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
