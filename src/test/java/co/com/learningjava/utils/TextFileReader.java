package co.com.learningjava.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TextFileReader {

    /** Lee un archivo del classpath (src/test/resources) y devuelve sus lineas. */
    public static List<String> readLines(String resourceName) {
        try (InputStream is = TextFileReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IllegalArgumentException("No se encontro el archivo en resources: " + resourceName);
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return content.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo " + resourceName, e);
        }
    }

    /** Devuelve el contenido completo con saltos de linea "\n", listo para escribir en la caja de texto. */
    public static String readText(String resourceName) {
        return String.join("\n", readLines(resourceName));
    }
}
