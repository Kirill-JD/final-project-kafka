plugins {
	java
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.ycan"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
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
