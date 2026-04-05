plugins {
	java
	id("org.springframework.boot") version Versions.springBoot
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.ycan"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(Versions.jvm)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects {
	apply(plugin = "java")
	repositories {
		mavenCentral()
		maven(url = "https://packages.confluent.io/maven/")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
