plugins {
	java
	`maven-publish`
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
	apply(plugin = "maven-publish")
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		// без VPN нормально не скачивает
		maven(url = "https://packages.confluent.io/maven/")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<JavaCompile> {
		options.release.set(Versions.jvm)
	}

	publishing {
		publications {
			create<MavenPublication>("mavenJava") {
				from(components["java"])
			}
		}
	}
}
