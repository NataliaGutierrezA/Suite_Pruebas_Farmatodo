plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "co.com.learningselenium"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allure {
    version.set("2.30.0")
    adapter {
        autoconfigure.set(true)
        aspectjVersion.set("1.9.22")
        frameworks {
            junit5 {
                adapterVersion.set("2.29.0")
            }
        }
    }
}

dependencies {
    // Selenium (incluye Chrome, Firefox, Edge, etc.)
    implementation("org.seleniumhq.selenium:selenium-java:4.25.0")

    // Allure (@Step y adjuntos se usan desde las paginas en src/main)
    implementation("io.qameta.allure:allure-java-commons:2.29.0")

    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    // Genera el reporte de Allure aunque haya tests fallidos
    finalizedBy("allureReport")
}

// Allure no sobrescribe un reporte existente; se limpia antes de regenerarlo
tasks.named("allureReport") {
    doFirst {
        delete(layout.buildDirectory.dir("reports/allure-report"))
    }
}
