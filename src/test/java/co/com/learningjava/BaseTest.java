package co.com.learningjava;

import co.com.learningjava.config.ConfigReader;
import co.com.learningjava.config.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver(ConfigReader.get("browser"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}