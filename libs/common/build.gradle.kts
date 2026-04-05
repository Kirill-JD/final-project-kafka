plugins {
    id("java")
}

group = "ru.ycan"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Deps.Other.jacksonAnnotations)
}

tasks.test {
    useJUnitPlatform()
}