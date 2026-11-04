package co.com.learningjava.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Calcula el resultado esperado (palabras, caracteres y frecuencias) a partir del texto, sin usar la pagina. */
public class WordFrequencyCounter {

    private final Map<String, Integer> frequencies = new LinkedHashMap<>();
    private int totalWords;
    private int totalCharacters;

    public WordFrequencyCounter(List<String> lines) {
        for (String line : lines) {
            // Caracteres con espacios, sin saltos de linea
            totalCharacters += line.length();

            for (String token : line.trim().split("\\s+")) {
                // Quitar signos de puntuacion y pasar a minuscula
                String word = token.toLowerCase().replaceAll("[^\\p{L}\\p{N}]", "");
                if (word.isEmpty()) continue;
                frequencies.merge(word, 1, Integer::sum);
                totalWords++;
            }
        }
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }

    public Map<String, Integer> getFrequencies() {
        return frequencies;
    }

    /** Formato igual al de la pagina en Keyword Density: "6 (43%)". */
    public String getDensity(String word) {
        int count = frequencies.getOrDefault(word, 0);
        long percent = Math.round(count * 100.0 / totalWords);
        return count + " (" + percent + "%)";
    }
}
