package co.com.learningjava.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class ScreenshotUtil {

    /** Adjunta una captura de pantalla al paso de Allure que se esta ejecutando. */
    public static void attach(WebDriver driver, String name) {
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png), "png");
    }
}
