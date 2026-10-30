package co.com.learningjava.pages;

import co.com.learningjava.utils.ScreenshotUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordCounterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By textBox          = By.id("box");
    private final By counter          = By.cssSelector("#top_counter .counted");
    private final By keywordToggle    = By.cssSelector("a[data-target='#kwd-accordion']");
    private final By keywordPanel     = By.id("kwd-accordion");
    private final By keywordRows      = By.cssSelector("#kwd-accordion-data a.list-group-item");
    private final By keywordWord      = By.cssSelector("span.word");
    private final By keywordValue     = By.cssSelector("span.badge");
    private final By detailRows       = By.cssSelector("#details_panel li.list-group-item[data-title]");
    private final By detailValue      = By.cssSelector("span.badge");

    public WordCounterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Abrir Word Counter")
    public void open(String url) {
        driver.get(url);
        ScreenshotUtil.attach(driver, "Word Counter abierto");
    }

    @Step("Escribir el texto en la caja")
    public void writeText(String text) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
        box.clear();
        box.sendKeys(text);
        wait.until(d -> !d.findElement(counter).getText().startsWith("0 words"));
        ScreenshotUtil.attach(driver, "Texto ingresado");
    }

    @Step("Obtener el resumen del contador")
    public String getCounterSummary() {
        String summary = driver.findElement(counter).getText().trim();
        ScreenshotUtil.attach(driver, "Resumen del contador");
        return summary;
    }

    /** Devuelve palabra -> "cantidad (porcentaje%)", en el orden en que las muestra en la seccion Keyword Density. */
    @Step("Obtener la densidad de palabras clave")
    public Map<String, String> getKeywordDensity() {
        expandKeywordDensity();
        wait.until(ExpectedConditions.presenceOfElementLocated(keywordRows));

        Map<String, String> keywords = new LinkedHashMap<>();
        for (WebElement row : driver.findElements(keywordRows)) {
            keywords.put(textOf(row.findElement(keywordWord)), textOf(row.findElement(keywordValue)));
        }
        ScreenshotUtil.attach(driver, "Keyword Density");
        return keywords;
    }

    /** Devuelve etiqueta -> valor de los items visibles de la seccion Details (ej. "Unique Words" -> "5"). */
    @Step("Obtener los detalles del texto")
    public Map<String, String> getDetails() {
        Map<String, String> details = new LinkedHashMap<>();
        for (WebElement row : driver.findElements(detailRows)) {
            if (!row.isDisplayed()) continue;
            details.put(row.getAttribute("data-title"), textOf(row.findElement(detailValue)));
        }
        ScreenshotUtil.attach(driver, "Details");
        return details;
    }

    private void expandKeywordDensity() {
        if (!driver.findElement(keywordPanel).isDisplayed()) {
            driver.findElement(keywordToggle).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(keywordPanel));
        }
    }

    private String textOf(WebElement element) {
        return ((String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].textContent;", element)).trim();
    }
}
