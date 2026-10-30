package co.com.learningjava;

import co.com.learningjava.config.ConfigReader;
import co.com.learningjava.pages.WordCounterPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldCounterTest extends BaseTest {

    private static final String TEXT =
            "farmatodo farmatodo farmatodo farmatodo farmatodo cuida cuida tu salud\n"
                    + "farmatodo cuida toda tu salud";

    private WordCounterPage wordCounter;

    @BeforeEach
    void openPage() {
        wordCounter = new WordCounterPage(driver);
        wordCounter.open(ConfigReader.get("baseUrl"));
        wordCounter.writeText(TEXT);
    }

    @Test
    void validarKeywordDensity() {
        Map<String, String> keywords = wordCounter.getKeywordDensity();
        System.out.println("Keyword Density: " + keywords);

        assertEquals("6 (43%)", keywords.get("farmatodo"));
        assertEquals("3 (21%)", keywords.get("cuida"));
        assertEquals("2 (14%)", keywords.get("tu"));
        assertEquals("2 (14%)", keywords.get("salud"));
    }

    @Test
    void validarDetails() {
        Map<String, String> details = wordCounter.getDetails();
        System.out.println("Details: " + details);

        assertTrue(wordCounter.getCounterSummary().startsWith("14 words"));

        assertEquals("14", details.get("Words"));
        assertEquals("99", details.get("Characters"));
    }
}
