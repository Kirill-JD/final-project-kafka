plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)
    annotationProcessor(Deps.Other.mapstructProcessor)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springBootStarterDataJpa)
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.jacksonDatabind)
    implementation(Deps.Other.jacksonDatatypeJsr310)
    implementation(Deps.Other.mapstruct)
    implementation(Deps.Other.picocli)
    implementation(Deps.Other.postgres)
    implementation(project(":libs:common"))
}

tasks.test {
    useJUnitPlatform()
}