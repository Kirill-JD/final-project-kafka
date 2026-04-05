plugins {
    `kotlin-dsl`
    id("com.github.ben-manes.versions") version "0.41.0"
}

repositories {
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation("com.github.ben-manes:gradle-versions-plugin:0.41.0")
}