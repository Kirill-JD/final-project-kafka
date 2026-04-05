plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springKafka)
    implementation(Deps.Other.jacksonDatabind)
    implementation(Deps.Other.jacksonDatatypeJsr310)
    implementation(project(":libs:kafka-helper"))
}

tasks.test {
    useJUnitPlatform()
}