plugins {
    id("java")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.0"
}

dependencies {
    implementation(Deps.Other.avro)
}