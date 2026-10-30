# Ejecución de los tests y reporte Allure

## Requisitos

- Java 21 instalado (`java -version`).
- El navegador configurado en `src/test/resources/config.properties` (`browser=firefox`; también admite `edge` y, por defecto, Chrome).
- Conexión a internet: los tests usan sitios públicos y la primera ejecución descarga el CLI de Allure.

No hace falta instalar Gradle ni Allure: se usa el wrapper (`./gradlew`).

## Pasos

1. **Abrir una terminal en la raíz del proyecto**
   ```bash
   cd page_object_base_project
   ```

2. **(Opcional) Revisar la configuración** en `src/test/resources/config.properties`:

   | Propiedad  | Uso                                 |
   |------------|-------------------------------------|
   | `browser`  | Navegador (`firefox`, `edge`, otro = Chrome) |
   | `baseUrl`  | URL de `WorldCounterTest` (Word Counter) |
   

3. **Ejecutar los tests y generar el reporte**
   ```bash
   ./gradlew clean test
   ```
   - Se abre el navegador y se ejecutan los tests.
   - Al terminar, `allureReport` corre automáticamente, incluso si algún test falla.
   - `clean` es opcional, pero evita mezclar resultados de ejecuciones anteriores.

4. **Ver el reporte en el navegador**
   ```bash
   ./gradlew allureServe
   ```
   Levanta un servidor local y abre el reporte. Se detiene con `Ctrl + C`.

   Si los tests fallan, Gradle no llega a `allureServe`. En ese caso usa:
   ```bash
   ./gradlew test allureServe --continue
   ```

## Ejecutar un solo test (opcional)

```bash
./gradlew test --tests "co.com.learningjava.WorldCounterTest"
```

## Rutas a revisar

| Qué                          | Ruta                                                        |
|------------------------------|-------------------------------------------------------------|
| Resultados crudos de Allure  | `build/allure-results/`                                     |
| Reporte HTML de Allure       | `build/reports/allure-report/allureReport/`                 |
| Reporte nativo de JUnit      | `build/reports/tests/test/index.html`                       |
| XML de resultados por clase  | `build/test-results/test/`                                  |

> El `index.html` de Allure no se ve bien abriéndolo directo con doble clic (queda vacío por restricciones del navegador con archivos locales). Usa `./gradlew allureServe`.

## Qué verás en el reporte

- Cada test con su resultado, duración y error (si falló).
- Los pasos ejecutados (`@Step`) de las páginas, en orden.
- Una captura de pantalla adjunta en cada paso.


## Problemas frecuentes

| Síntoma | Causa / solución |
|---------|------------------|
| `Target directory ... is already in use` | Ya existe un reporte anterior. El proyecto lo borra antes de regenerar; si ocurre, ejecuta `./gradlew clean test`. |
| El reporte sale vacío | Se abrió el `index.html` directo. Usa `./gradlew allureServe`. |
| No se abre el navegador | Verifica que `browser` en `config.properties` corresponda a un navegador instalado. |
