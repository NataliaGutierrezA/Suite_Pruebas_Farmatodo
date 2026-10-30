package co.com.learningjava.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return new FirefoxDriver();
            case "edge":
                return new EdgeDriver();
            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                // options.addArguments("--headless=new"); // sin interfaz
                return new ChromeDriver(options);
        }
    }
}