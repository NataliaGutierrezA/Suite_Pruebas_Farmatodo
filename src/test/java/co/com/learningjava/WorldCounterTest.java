package co.com.learningjava;

import co.com.learningjava.config.ConfigReader;
import co.com.learningjava.pages.WordCounterPage;
import co.com.learningjava.utils.TextFileReader;
import co.com.learningjava.utils.WordFrequencyCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldCounterTest extends BaseTest {

    private static final String FILE = "texto.txt";

    private WordCounterPage wordCounter;
    private WordFrequencyCounter expected;

    @BeforeEach
    void openPage() {
        // Texto y resultado esperado salen del archivo, no de valores escritos a mano
        expected = new WordFrequencyCounter(TextFileReader.readLines(FILE));

        wordCounter = new WordCounterPage(driver);
        wordCounter.open(ConfigReader.get("baseUrl"));
        wordCounter.writeText(TextFileReader.readText(FILE));
    }

    @Test
    void validarKeywordDensity() {
        Map<String, String> keywords = wordCounter.getKeywordDensity();
        System.out.println("Keyword Density (pagina): " + keywords);
        System.out.println("Frecuencias (esperado): " + expected.getFrequencies());

        assertTrue(!keywords.isEmpty(), "La pagina no mostro palabras en Keyword Density");

        // Cada palabra que muestra la pagina debe coincidir con la calculada desde el archivo
        keywords.forEach((word, value) ->
                assertEquals(expected.getDensity(word), value, "Densidad de '" + word + "'"));

        // Toda palabra repetida en el archivo debe aparecer en la pagina
        expected.getFrequencies().forEach((word, count) -> {
            if (count > 1) {
                assertTrue(keywords.containsKey(word), "La pagina no muestra la palabra '" + word + "'");
            }
        });
    }

    @Test
    void validarDetails() {
        Map<String, String> details = wordCounter.getDetails();
        System.out.println("Details: " + details);

        assertTrue(wordCounter.getCounterSummary().startsWith(expected.getTotalWords() + " words"));

        assertEquals(String.valueOf(expected.getTotalWords()), details.get("Words"));
        assertEquals(String.valueOf(expected.getTotalCharacters()), details.get("Characters"));
    }
}
