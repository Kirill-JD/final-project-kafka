plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)
    annotationProcessor(Deps.Other.mapstructProcessor)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springBootStarterDataElasticsearch)
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.elasticsearch)
    implementation(Deps.Other.jacksonDatabind)
    implementation(Deps.Other.jacksonDatatypeJsr310)
    implementation(Deps.Other.mapstruct)
    implementation(Deps.Other.picocli)
    implementation(project(":libs:common"))
}